import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExceptionDefTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "ExceptionDef"

  it should "Define a new exception" in {
    val ex = ExceptionClassDef("Exception", Field("public", "reason")).evalClass
    val ex2 = ExceptionClassDef("NewException", Field("public", "message")).evalClass
    ex.toString.contains("Exception") shouldBe true
    ex2.toString.contains("NewException") shouldBe true
  }
}