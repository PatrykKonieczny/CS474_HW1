import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class SetDifferenceTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Set Difference Operation"

  it should "return the set difference of  setName and otherSet" in {
    Assign("differenceSet", SetDifference(Assign("setName", Insert(1,2,3,4)),Assign("otherSet",Insert(2,5,4)))).eval
    Check("differenceSet", 1).checkItem shouldBe true
    Check("differenceSet", 2).checkItem shouldBe false
  }
}