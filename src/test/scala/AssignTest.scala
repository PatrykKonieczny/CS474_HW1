import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class AssignTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Assign"

  it should "Insert a set of items 1,2,4 into someSetName" in {
    Assign("someSetName", Insert(1,2,4)).eval
    Check("someSetName", 1).checkItem shouldBe true
  }
}
