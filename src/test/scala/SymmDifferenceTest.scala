import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class SymmDifferenceTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Symmetrical Difference operation"

  it should "Insert a set of items 1,2,4 into SetName and 2,5 in otherSet to find their  Symmetrical" +
    "difference" in {
    Assign("symmetricalSet", SymmDifference(Assign("setName", Insert(1,2,3,4)),Assign("otherSet",Insert(2,5)))).eval
    Check("symmetricalSet", 1).checkItem shouldBe true
  }
}
