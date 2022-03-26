import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImplementsTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Implements Data type"

  it should "Test the implmentation of interfaces" in {
    val anInterface = InterfaceDecl("I99", Field("public", "fff"), AbstractMethod("m2"), AbstractMethod("m477")).evalClass
    val otherInterface = InterfaceDecl("I98", Field("public", "fff"), AbstractMethod("m2"), AbstractMethod("m5")).evalClass
    val someOtherClass = ClassDef("someOtherClass",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m5", Union(Insert(1), Insert(3)))).evalClassDef
    val aClass = ClassDef("c44",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m5", Union(Insert(1), Insert(3)))).evalClassDef
    NewObject("someOtherClass", "var").evalClass
    NewObject("c44", "fff").evalClass
    Implements("var", "I98").evalClass

    val exp = InvokeMethod("var", "m5").evalClass
    exp shouldBe Union(Insert(1), Insert(3)).eval
    the [Error] thrownBy Implements("I99", "I98").evalClass should have message "An Interface can not implement an Interface"
    the [Error] thrownBy Implements("fff", "I99").evalClass should have message "Class method dosn't implement interface"
  }
}