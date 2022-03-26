import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InterfaceTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Declaring a new Interface"

  it should "Declare a new interface that could be used" in {
    val someInterface = InterfaceDecl("someInterface", Field("final", "val"), AbstractMethod("m1"), AbstractMethod("m2")).evalClass
    val basicInterface = InterfaceDecl("basicInterface", Field("final", "item")).evalClass
    the [Error] thrownBy InterfaceDecl("I10", Field("final", "item"), Method("public","m1", Union(Insert(1), Insert(3)))).evalClass should have message "Concrete Methods are not allowed in interface"
  }
}