import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CatchTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Catch"

  it should "Catch an exception after it is thrown" in {
    ExceptionClassDef("Exception", Field("public", "Reason")).evalClass
    ExceptionClassDef("Exception2", Field("public", "Reason")).evalClass
    CatchException("Exception",
      Assign("val", Insert(1,2,3)),
      Assign("union" , Union(Insert(1,2,3), Insert("a"))),
      IF(Check("val", 4),
        Assign("x", Insert("A", "B", "C")),
        ThrowException("Exception", "check failed")),
      Assign("val", Insert("x", "y", "z")),
      Catch("Exception", Assign("value", Insert("caught")))
    ).eval
    CatchException("Exception2",
      Assign("something", Insert(1,2,3)),
      IF(Check("val", 4),
        Assign("Z", Insert("A", "B", "C")),
        ThrowException("Exception2", "failed")),
      Assign("something", Insert(1000,"2835H")),
      Catch("Exception2", Assign("Message", GetExceptionMessage("Exception2", "Reason")))
    ).eval
    Check("Message", "failed").checkItem shouldBe true
    Check("val", 1).checkItem shouldBe true
    Check("union", "a").checkItem shouldBe true
    Check("val", "x").checkItem shouldBe false
    Check("value", "caught").checkItem shouldBe true
  }
}