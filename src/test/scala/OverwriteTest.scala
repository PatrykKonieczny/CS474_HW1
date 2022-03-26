import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OverwriteTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Overwriting a method in a Class"

  it should "The child class should overwrite the parent class that has the same method name" in {
    val someClass = ClassDef("someNewClass",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m8", Union(Insert(1), Insert(3)))).evalClassDef
    val antherClass = ClassDef("anotherClass",Field("public","ff"), Constructor(AssignField("ff", 90)),Method("public","m8",Intersect(Insert(1, 2), Insert(3, 2)))).evalClassDef
    val ext = Extends("someNewClass", "anotherClass").evalClass
    NewObject("anotherClass", "value").evalClass
    val methodCall = InvokeMethod("value", "m8").evalClass
    methodCall shouldBe  Intersect(Insert(1, 2), Insert(3, 2)).eval
  }
}