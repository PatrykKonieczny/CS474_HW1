import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObjectCreation extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "New object in relation to abstract classes and interfaces"

  it should "Throw an error is the user tries to intanitate an interface or abstract class" in {
    val newInterface = InterfaceDecl("newInterface", Field("final", "val"), AbstractMethod("m1"), AbstractMethod("m2")).evalClass
    val newAbstractClass = AbstractClassDef("newAbstract", Field("public","x"), Constructor(AssignField("x", 5)), Method("public","m1", Union(Insert(1), Insert(3))), AbstractMethod("m2")).evalAbstractClass
    val newConcreteClass = ClassDef("newConcrete",Field("public","x"), Constructor(AssignField("x", 5)),Method("public","m1", Union(Insert(1), Insert(3)))).evalClassDef

    the [Error] thrownBy NewObject("newInterface", "I1").evalClass should have message "Cannot instantiate an Interface"
    the [Error] thrownBy NewObject("newAbstract", "A2").evalClass should have message "Cannot instantiate an Abstract Class"
  }
}