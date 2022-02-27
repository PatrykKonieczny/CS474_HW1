

import SetDSL.{BindingScope, ClassDefinition, ClassOps, ParentTable, SetOps, SetType, field, method}

import java.util
import scala.collection.mutable
import scala.runtime.Nothing$

class SetDSL:
  def assignSet(name: String, set1:SetDSL.SetType) :SetDSL.SetType =
    if(SetDSL.BindingScope.contains(name))
      SetDSL.BindingScope(name)
    else
      SetDSL.BindingScope += (name -> set1)
      SetDSL.BindingScope(name)

  /*
  Function that checks the value of a set - Takes a set name parameter that is a string and a value parameter that
  is being checked. If found then return true else return false
  */
  def checkVal(set1: String, value: Any): Boolean =
    if(SetDSL.BindingScope.contains(set1))
      if(SetDSL.BindingScope(set1).contains(value))
        true
      else
        false
    else
      false


  /*
  Function for deleting a value in a set - Takes a string as a parameter which is the set name,
  and a value parameter which is the value that should be deleted - Returns a set with deleted value or
  the old set. Is the set cant be found an empty set is returned.
  */
  def deleteVal(set1:String, value:Any): SetDSL.SetType =
    if(SetDSL.BindingScope.contains(set1))
      if(SetDSL.BindingScope(set1).contains(value))
        SetDSL.BindingScope(set1) -= value
        SetDSL.BindingScope(set1)
      else
        SetDSL.BindingScope(set1)
    else
      scala.collection.mutable.Set.empty[Any]


  /*
    This method creates a new class definiton and stores it by name in the class definition Map
  */
  def defineClass(name: String, f: SetDSL.field, constructor: collection.mutable.Map[String,Any], method: SetDSL.method): SetDSL.classDef =
    if(SetDSL.ClassDefinition.contains(name))
      ClassDefinition(name)
    else
      SetDSL.ClassDefinition += (name -> new SetDSL.classDef(f, constructor, method))
      ClassDefinition(name)

  /*
    This method creates a new instance of a class name and binds it to a variable.
    It calls the constructor which sets a value to a field
  */
  def createClass(name: String, variable: String): Any =
    if(SetDSL.ClassDefinition.contains(name))
      SetDSL.ClassBinding += (variable -> SetDSL.ClassDefinition(name))
      val fName = SetDSL.ClassBinding(variable).fields.fieldName
      SetDSL.ClassBinding(variable).fields.fieldVal += (fName -> SetDSL.ClassBinding(variable).construct(fName))
      SetDSL.ClassBinding(variable)
    else
      None

  /*
    This method invokes a method from a created bounded class. It returns the result of the expressions,
    which are defined in the SetOps. It also checks if the class has a parentClass. If the parentClass
    has the same method name as the child. The parent gets overwritten.
  */
  def invoke(className: String, methodName: String): Any =
    if(SetDSL.ClassBinding.contains(className))
      if(SetDSL.ParentTable.contains(className))
        val parent = SetDSL.ParentTable(className)
        val parentMethod = SetDSL.ClassDefinition(parent).methods.methodName
        if(SetDSL.ClassBinding(className).methods.expressions.contains(parentMethod))
          val classMeth = SetDSL.ClassBinding(parent).methods.expressions(methodName)
          classMeth.eval
        else
          val classMeth = SetDSL.ClassBinding(className).methods.expressions(methodName)
          classMeth.eval
      else
        val classMeth = SetDSL.ClassBinding(className).methods.expressions(methodName)
        classMeth.eval
    else
      None

  /*
  This method creates a parent child relationship between two classes.
  It does this by mapping the child class to the parent class in the ParentTable
  */
  def extendAClass(parentClass: String, extendingClass: String): String =
    if(!ParentTable.contains(extendingClass))
      SetDSL.ParentTable += (extendingClass -> parentClass)
      ParentTable(extendingClass)
    else
      ParentTable(extendingClass)


object SetDSL:
  import scala.collection.mutable.Map
  type SetType = scala.collection.mutable.Set[Any]
  private val BindingScope: Map[String, SetType] = scala.collection.mutable.Map()
  private val ClassDefinition: mutable.Map[String, classDef] = scala.collection.mutable.Map()
  private val ClassBinding: mutable.Map[String, classDef] = scala.collection.mutable.Map()
  private val ParentTable: mutable.Map[String, String] = scala.collection.mutable.Map()

  // a field class that holds data that a field needs
  class field(axsType: String, name: String):
    val accessType: String = axsType
    val fieldName: String = name
    val fieldVal: collection.mutable.Map[String,Any] = mutable.Map()

  // a method class that holds data that a method needs
  class method(axsType: String, name:String, exp: SetOps):
    val accessType: String = axsType
    val methodName: String = name
    val expressions: mutable.Map[String, SetOps] = mutable.Map(name -> exp)

  //a classDef class that holds data that a class needs
  class classDef(f: field, const: scala.collection.mutable.Map[String, Any], meth: method):
    val fields: field = f
    val construct: scala.collection.mutable.Map[String, Any] = const
    val methods: method = meth



  //Class operations defined below
  enum ClassOps:
    case Field(accessType:String, name: String*)
    case Method(accessType:String, name: String, exp: SetOps*)
    case AssignField(name:String, item:Any)
    case Constructor(exp: ClassOps*)
    case ClassDef(name: String, field: ClassOps, constructor: ClassOps, method: ClassOps)
    case NewObject(name: String, variable : String)
    case Extends(parentClass: String, childClass: String)
    case InvokeMethod(className: String, methodName: String)

    // evaluate a class field to return a new field instance
    def evalField: field = {
          this match{
            case Field(accessType, name) => new field(accessType, name)
          }
    }

    // evaluate a class constructor to return a maping that assigns a field variable to Any
    def evalConstructor: mutable.Map[String, Any] = {
      this match{
        case AssignField(name, item) => mutable.Map(name -> item)
        case Constructor(exp) =>  exp.evalConstructor
      }
    }

    // evaluate a class method to return instance of a method
    def evalMethod: method = {
      this match{
        case Method(accessType, name, exp) => new method(accessType, name, exp)
      }
    }

    // evaluate a class definition in order to return a instance of a new class defintion
    def evalClassDef: classDef = {
      this match{
        case ClassDef(name, field, constructor, method) => new SetDSL().defineClass(name, field.evalField , constructor.evalConstructor, method.evalMethod)
      }
    }

    // evaluate the rest of the types of cases in the class language that return type Any.
    def evalClass : Any = {
      this match{
        case NewObject(name, variable) => new SetDSL().createClass(name, variable)
        case InvokeMethod(className, methodName) => new SetDSL().invoke(className, methodName)
        case Extends(parentClass, childClass) => new SetDSL().extendAClass(parentClass, childClass)
      }
    }

  /*
  Set operation language defined below
  */
  enum SetOps:
    case Var(input: String)
    case Insert(input: Any*)
    case Delete(set1: String, input: Any)
    case Union(set1: SetOps, set2: SetOps)
    case Intersect(set1: SetOps, set2: SetOps)
    case SetDifference(set1: SetOps, set2: SetOps)
    case SymmDifference(set1: SetOps, set2: SetOps)
    case CartProduct(set1: SetOps , set2: SetOps)
    case Assign (obj1: String, obj2: SetOps)
    case Check(obj1: String , obj: Any)
    case Macro(name: String, obj2: SetOps)
    case Scope(name: String, obj2: SetOps)

    /*
    Function that inserts elements of Any type into a set, which are passed through as parameters. -
    The set with the new elements is returned.
    */
    def insertElem( elem: Any*): scala.collection.mutable.Set[Any] =
      val newSet = scala.collection.mutable.Set.empty[Any]
      elem.foreach(e => newSet += e)
      newSet


    /*
    Function that checks the value of a set - Takes a set name parameter that is a string and a value parameter that
    is being checked. If found then return true else return false
    */
    def checkVal(set1: String, value: Any): Boolean =
      if(BindingScope.contains(set1))
        if(BindingScope(set1).contains(value))
          true
        else
          false
      else
        false


    /*
    Function for Cartesian Product - Loops through to and adds a tuple of the cross to a
    new set and returns that set - Takes 2 SetType parameters
     */
    def crossProduct(set1: SetType, set2: SetType) : SetType = {
      val crossSet = scala.collection.mutable.Set.empty[Any]
      set1.foreach(i => set2.foreach(j => crossSet += ((i,j))))
      crossSet
    }


    // Function to evaluate value checks in sets - Returns a boolean
    def checkItem: Boolean =
      this match {
        case Check(obj1, obj2) => checkVal(obj1,obj2)
      }

    // Function to evaluate set operations - Returns a SetType
    def eval: SetType = {
      this match{
        case Var(input) => BindingScope(input)
        case Insert( elem*) => insertElem( elem*)
        case Delete(set1, elem) => new SetDSL().deleteVal(set1, elem)
        case Union(set1, set2) => set1.eval | set2.eval
        case Intersect(set1, set2) => set1.eval & set2.eval
        case SetDifference(set1, set2) => set1.eval &~ set2.eval
        case SymmDifference(set1, set2) => (set1.eval -- set2.eval) | (set2.eval -- set1.eval)
        case CartProduct(set1, set2) => crossProduct(set1.eval, set2.eval)
        case Assign(obj1, obj2) => new SetDSL().assignSet(obj1, obj2.eval)
        case Macro(name, obj2) => new SetDSL().assignSet(name, obj2.eval)
        case Scope(name: String, obj2: SetOps) => new SetDSL().assignSet(name, obj2.eval)
      }
    }


  @main def hello() : Unit = {
    import SetOps.*
    import ClassOps.*

  }

