import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IFTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "If branching"

  it should "evaluate a condition then excedute a set operation based on that condition" in {
    Assign("vals", Insert(1,2,3)).eval
    IF(Check("vals", 1), Assign("x", Insert("A", "B", "C")), Assign("y", Insert("Fail"))).eval
    IF(Check("vals", 5), Assign("z", Insert("A", "B", "C")), Assign("a", Insert("Fail"))).eval
    Check("x", "A").checkItem shouldBe true
    Check("y", "Fail").checkItem shouldBe false
    Check("z", "C").checkItem shouldBe false
    Check("a", "Fail").checkItem shouldBe true
  }
}