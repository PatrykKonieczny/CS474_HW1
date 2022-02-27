import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InvokeMethodTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "InvokeMethod"

  it should "Create a new class definition and initialize it. Then invoke a method from that class" in {
    val someClass = ClassDef("classNew",Field("public","x"), Constructor(AssignField("x", 5)),Method("public","m1", Union(Insert(1), Insert(3)))).evalClassDef
    NewObject("classNew", "aClass").evalClass
    val methodCall = InvokeMethod("aClass", "m1").evalClass
    methodCall shouldBe  Union(Insert(1), Insert(3)).eval
  }
}