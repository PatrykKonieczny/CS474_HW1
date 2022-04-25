import SetDSL.SetOps.Var
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class ComplimentOpTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Compliment optimization functions that was implemented"

  it should "Return a empty set when made in " in {
    val s1:SetDSL.SetType = collection.mutable.Set.empty
    Assign("gg", Insert(1,2,4)).eval
    val r1 = SetDifference(Var("gg"),Var("gg")).eval
    val result = new SetDSL().complimentEval(r1)
    result shouldBe s1
  }
}