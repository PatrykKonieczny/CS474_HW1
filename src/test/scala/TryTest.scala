import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TryTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "CatchException"

  it should "Evaluate the CatchException (try) data type " in {
    ExceptionClassDef("Ex", Field("public", "Reason")).evalClass
    CatchException("Ex",
      Assign("someThing", Insert(1,2,3)),
      Assign("union" , Union(Insert(1,2,3), Insert("a"))),
      Assign("var", Insert("x", "y", "z"))
    ).eval

    the [Error] thrownBy CatchException("NewExp", Assign("c", Insert("C"))).eval should have message "Exception not defined"
    Check("union", "a").checkItem shouldBe true
    Check("someThing", 2).checkItem shouldBe true
    Check("var", "y").checkItem shouldBe true
  }
}
