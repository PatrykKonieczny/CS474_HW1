

import SetDSL.{AbstractClassTable, BindingScope, ClassBinding, ClassDefinition, ClassOps, ImplementTable, InterfaceTable, ParentTable, SetOps, SetType, field, method}

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
    It calls the constructor which sets a value to a field. Prevents instantiation of a interface and abstract class.
    This method also check if a class has a parent. Then the parents constructor should also be called before the child constructor.
  */
  def createClass(name: String, variable: String): Any =
    if(SetDSL.InterfaceTable.contains(name))
      throw new Error("Cannot instantiate an Interface")
    else if(SetDSL.AbstractClassTable.contains(name))
      throw new Error("Cannot instantiate an Abstract Class")
    if(SetDSL.ClassDefinition.contains(name) && !SetDSL.ParentTable.contains(name))
      SetDSL.ClassBinding += (variable -> SetDSL.ClassDefinition(name))
      val fName = SetDSL.ClassBinding(variable).fields.fieldName
      SetDSL.ClassBinding(variable).fields.fieldVal += (fName -> SetDSL.ClassBinding(variable).construct(fName))
      SetDSL.ClassBinding(variable)
    else if (SetDSL.ClassDefinition.contains(name) && SetDSL.ParentTable.contains(name))
      val parent = ParentTable(name)
      if(AbstractClassTable.contains(parent))
        val fieldName = SetDSL.AbstractClassTable(parent).fields.fieldName
        SetDSL.AbstractClassTable(parent).fields.fieldVal += (fieldName-> SetDSL.AbstractClassTable(parent).constructor(fieldName))
        SetDSL.ClassBinding += (variable -> SetDSL.ClassDefinition(name))
        SetDSL.ParentTable += (variable -> parent)
      else if (ClassDefinition.contains(parent))
        SetDSL.ClassBinding += (parent -> SetDSL.ClassDefinition(parent))
        val fieldName = SetDSL.ClassBinding(parent).fields.fieldName
        SetDSL.ClassBinding(parent).fields.fieldVal += (fieldName -> SetDSL.ClassBinding(parent).construct(fieldName))
        SetDSL.ClassBinding += (variable -> SetDSL.ClassDefinition(name))
        val childField = SetDSL.ClassBinding(variable).fields.fieldName
        SetDSL.ClassBinding(variable).fields.fieldVal += (childField -> SetDSL.ClassBinding(variable).construct(childField))
        SetDSL.ParentTable += (variable -> parent)
    else
      None

  /*
    This method invokes a method from a created bounded class. It returns the result of the expressions,
    which are defined in the SetOps. It also checks if the class has a parentClass. If the parentClass
    has the same method name as the child. The parent gets overwritten.
  */
  def invoke(className: String, methodName: String): Any =
    if(SetDSL.ClassBinding.contains(className) && SetDSL.ParentTable.contains(className))
      val parent = SetDSL.ParentTable(className)
      if(SetDSL.AbstractClassTable.contains(parent) && !SetDSL.ClassBinding(className).methods.expressions.contains(methodName))
        val parentMethod = SetDSL.AbstractClassTable(parent).methods
        val meth : collection.mutable.Set[method] = collection.mutable.Set.empty
        parentMethod.foreach(u => if u.methodName == methodName then meth += u)
        meth.head.expressions(methodName).eval
      else if(SetDSL.AbstractClassTable.contains(parent) && SetDSL.ClassBinding(className).methods.expressions.contains(methodName))
        val classMeth = SetDSL.ClassBinding(className).methods.expressions(methodName)
        classMeth.eval
      else if(!SetDSL.ClassBinding(className).methods.expressions.contains(SetDSL.ClassBinding(parent).methods.methodName))
          val classMeth = SetDSL.ClassBinding(parent).methods.expressions(methodName)
          classMeth.eval
      else
          val classMeth = SetDSL.ClassBinding(className).methods.expressions(methodName)
          classMeth.eval
    else if(SetDSL.ClassBinding.contains(className))
      val classMeth = SetDSL.ClassBinding(className).methods.expressions(methodName)
      classMeth.eval
    else None




  /*
  This method creates a parent child relationship between two classes.
  It does this by mapping the child class to the parent class in the ParentTable.
  Prevents a class from extending itself. Prevents a Class from extending a an Interface, and an Interface from
  extending a class.
  */
  def extendAClass(parentClass: String, extendingClass: String): String =
    if(parentClass == extendingClass)
      throw new Error("Class cannot extend itself")
    if(ClassDefinition.contains(parentClass) && InterfaceTable.contains(extendingClass))
      throw new Error("Interface cannot extend a concrete Class")
    if(InterfaceTable.contains(parentClass) && (AbstractClassTable.contains(extendingClass) || ClassDefinition.contains(extendingClass)))
      throw new Error("Interface cannot extend an abstract Class or an concrete Class")
    else if(AbstractClassTable.contains(extendingClass) && ClassDefinition.contains(parentClass))
      val abstractMethods = AbstractClassTable(extendingClass).methods
      val parent = ClassDefinition(parentClass).methods.methodName
      val alikeMethods: collection.mutable.Set[String] = collection.mutable.Set.empty
      abstractMethods.foreach(u => if (u.methodName == parent) && (u.expressions == Map()) then alikeMethods += u.methodName)
      if(alikeMethods.isEmpty)
        throw new Error("Class does implement extended abstract method. Both classes should be abstract")
    if(!ParentTable.contains(extendingClass))
      SetDSL.ParentTable += (extendingClass -> parentClass)
      ParentTable(extendingClass)
    else
      ParentTable(extendingClass)

  //returns a new concrete method
  def addConcreteMethod(axsType: String, name:String, exp: SetDSL.SetOps) : SetDSL.method =
    val newMethod = new method(axsType, name)
    newMethod.addExpression(exp)
    newMethod

  /*
  This function helps define an Abstract Class. It stores the abstract Class in the Abstract Class Table.
  If there is no abstract method in the definition then an error is issued.
  */
  def defineAbstractClass(name: String, field: SetDSL.field, constructor: collection.mutable.Map[String,Any], method: SetDSL.ClassOps*): SetDSL.abstractClass =
    if(SetDSL.AbstractClassTable.contains(name))
      SetDSL.AbstractClassTable(name)
    else
      val classMethods : collection.mutable.Set[SetDSL.method] = collection.mutable.Set.empty
      method.foreach(e => classMethods += e.evalMethod)
      SetDSL.AbstractClassTable += (name -> new SetDSL.abstractClass(field, constructor, classMethods))
      val abstractMethods: collection.mutable.Set[String] = collection.mutable.Set.empty
      val AbstractClassMethods = SetDSL.AbstractClassTable(name).methods
      AbstractClassMethods.foreach(u => if u.expressions == Map() then abstractMethods += u.methodName)
      if(abstractMethods.isEmpty)
        throw new Error("Abstract method needed")
      SetDSL.AbstractClassTable(name)

  /*
  This function declares a new interface and stores it in the Interface Table. The function
  also throws an error if there are concrete methods in the function.
  */
  def declareInterface(name: String, f: SetDSL.field, method: SetDSL.ClassOps*) : Unit =
    if(SetDSL.InterfaceTable.contains(name))
      SetDSL.InterfaceTable(name)
    else
      val interfaceMethods: collection.mutable.Set[SetDSL.method] = collection.mutable.Set.empty
      val concreteMethods: collection.mutable.Set[String] = collection.mutable.Set.empty
      method.foreach(e => interfaceMethods += e.evalMethod)
      interfaceMethods.foreach(e => if e.expressions != Map() then concreteMethods += e.methodName)
      if(concreteMethods.nonEmpty && interfaceMethods.nonEmpty)
        throw new Error("Concrete Methods are not allowed in interface")
      SetDSL.InterfaceTable += (name -> new SetDSL.interface(f, interfaceMethods))

  /*
  This function helps implement an Interface. It stores className that implements a certain Interface.
  This information is stored in the Interface Table. The function also prevents a interface from implementing an
  interface and prevents the user from implementing a interface that does not have implementation in the concrete class.
  */
  def implementInterface(className: String, InterfaceName: String): String =
    if(SetDSL.InterfaceTable.contains(className))
      throw new Error("An Interface can not implement an Interface")
    if(!SetDSL.ImplementTable.contains(className))
      val interfaceMeth = InterfaceTable(InterfaceName).methodTable
      val alikeMethods: collection.mutable.Set[String] = collection.mutable.Set.empty
      val classMethod = SetDSL.ClassBinding(className).methods.methodName
      interfaceMeth.foreach(u => if u.methodName ==  classMethod then alikeMethods += u.methodName)
      if alikeMethods.isEmpty then throw Error("Class method dosn't implement interface")
      SetDSL.ImplementTable += (className -> InterfaceName)
      SetDSL.ImplementTable(className)
    else
      SetDSL.ImplementTable(className)

object SetDSL:
  import scala.collection.mutable.Map
  type SetType = scala.collection.mutable.Set[Any]
  private val BindingScope: mutable.Map[String, SetType] = scala.collection.mutable.Map()
  private val ClassDefinition: mutable.Map[String, classDef] = scala.collection.mutable.Map()
  private val ClassBinding: mutable.Map[String, classDef] = scala.collection.mutable.Map()
  private val ParentTable: mutable.Map[String, String] = scala.collection.mutable.Map()
  private val InterfaceTable: mutable.Map[String, interface] = scala.collection.mutable.Map()
  private val AbstractClassTable: mutable.Map[String, abstractClass] = scala.collection.mutable.Map()
  private val ImplementTable: mutable.Map[String, String] = scala.collection.mutable.Map()

  // a field class that holds data that a field needs
  class field(axsType: String, name: String):
    val accessType: String = axsType
    val fieldName: String = name
    val fieldVal: collection.mutable.Map[String,Any] = mutable.Map()

  //a method class that holds data that a method needs
  class method(axsType: String, name:String):
    val accessType: String = axsType
    val methodName: String = name
    val expressions: mutable.Map[String, SetOps] = mutable.Map()

    //method to add expression to a method
    def addExpression(exp: SetOps): Unit =
      expressions += (methodName -> exp)



  //a classDef class that holds data that a class needs
  class classDef(f: field, const: scala.collection.mutable.Map[String, Any], meth: method):
    val fields: field = f
    val construct: scala.collection.mutable.Map[String, Any] = const
    val methods: method = meth

  //An abstractClass class that holds data needed for an abstract class
  class abstractClass(f:field, const: scala.collection.mutable.Map[String, Any], meth: scala.collection.mutable.Set[method]):
    val fields: field = f
    val constructor: scala.collection.mutable.Map[String, Any] = const
    val methods: scala.collection.mutable.Set[method] = meth

  //An interface class that holds data needed for an interface
  class interface(f: field, meth:scala.collection.mutable.Set[method] ):
    val fields: field = f
    val methodTable: scala.collection.mutable.Set[method] = meth

  //Class operations defined below
  enum ClassOps:
    case Field(accessType:String, name: String*)
    case Method(accessType:String, name: String, exp: SetOps*)
    case AbstractMethod(name: String)
    case AssignField(name:String, item:Any)
    case Constructor(exp: ClassOps*)
    case ClassDef(name: String, field: ClassOps, constructor: ClassOps, method: ClassOps)
    case NewObject(name: String, variable : String)
    case Extends(parentClass: String, childClass: String)
    case InvokeMethod(className: String, methodName: String)
    case AbstractClassDef(name: String, field: ClassOps, constructor: ClassOps, method: ClassOps*)
    case InterfaceDecl(name: String, field: ClassOps, methods: ClassOps*)
    case Implements(InterfaceName: String, ImplmentationName: String)

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
        case Method(accessType, name, exp) => new SetDSL().addConcreteMethod(accessType, name, exp)
        case AbstractMethod(name) => new method("public", name)
      }
    }

    // evaluate a class definition in order to return a instance of a new class defintion
    def evalClassDef: classDef = {
      this match{
        case ClassDef(name, field, constructor, method) => new SetDSL().defineClass(name, field.evalField , constructor.evalConstructor, method.evalMethod)
      }
    }

    //Abstract Class evaluation, to return an instance of a abstract class
    def evalAbstractClass: abstractClass = {
      this match{
        case AbstractClassDef(name, field, constructor, meth*) => new SetDSL().defineAbstractClass(name, field.evalField, constructor.evalConstructor, meth*)
      }
    }


    // evaluate the rest of the types of cases in the class language that return type Any.
    def evalClass : Any = {
      this match{
        case NewObject(name, variable) => new SetDSL().createClass(name, variable)
        case InvokeMethod(className, methodName) => new SetDSL().invoke(className, methodName)
        case Extends(parentClass, childClass) => new SetDSL().extendAClass(parentClass, childClass)
        case InterfaceDecl(name, f, methods*) => new SetDSL().declareInterface(name, f.evalField, methods*)
        case Implements(className, interfaceName) => new SetDSL().implementInterface(className, interfaceName)
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

