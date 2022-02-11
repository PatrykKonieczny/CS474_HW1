

import java.util
import scala.collection.mutable

object SetDSL:
  type SetType = scala.collection.mutable.Set[Any]
  private var BindingScope: Map[String, SetType] = Map()
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
    Function that creates a binding between a string name and a set of SetType, Adds this binging to the Binding scope Map
     - Returns the set that is assigned
    */
    def assignSet(name: String, set1:SetType) =
      if(BindingScope.contains(name))
        BindingScope(name)
      else
        BindingScope += (name -> set1)
        BindingScope(name)

    /*
    Function that checks the value of a set - Takes a set name parameter that is a string and a value parameter that
    is being checked. If found then return true else return false
    */
    def checkVal(set1: String, value: Any) =
      if(BindingScope.contains(set1))
        if(BindingScope(set1).contains(value))
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
    def deleteVal(set1:String, value:Any) =
      if(BindingScope.contains(set1))
        if(BindingScope(set1).contains(value))
          BindingScope(set1) -= value
          BindingScope(set1)
        else
          BindingScope(set1)
      else
        scala.collection.mutable.Set.empty[Any]

    /*
    Function for Cartesian Product - Loops through to and adds a tuple of the cross to a
    new set and returns that set - Takes 2 SetType parameters
     */
    def crossProduct(set1: SetType, set2: SetType) = {
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
        case Delete(set1, elem) => deleteVal(set1, elem)
        case Union(set1, set2) => set1.eval | set2.eval
        case Intersect(set1, set2) => set1.eval & set2.eval
        case SetDifference(set1, set2) => set1.eval &~ set2.eval
        case SymmDifference(set1, set2) => (set1.eval -- set2.eval) | (set2.eval -- set1.eval)
        case CartProduct(set1, set2) => crossProduct(set1.eval, set2.eval)
        case Assign(obj1, obj2) => assignSet(obj1, obj2.eval)
        case Macro(name, obj2) => assignSet(name, obj2.eval)
        case Scope(name: String, obj2: SetOps) => assignSet(name, obj2.eval)
      }

    }


  @main def hello : Unit = {
    import SetOps.*
  }

