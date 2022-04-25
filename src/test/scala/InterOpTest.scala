import SetDSL.SetOps.Var
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class InterOpTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Intersect optimization functions that was implemented"

  it should "Return a empty set when intersect with empty set" in {
    val s1:SetDSL.SetType = collection.mutable.Set.empty
    Assign("gg", Insert(1,2,4)).eval
    val r1 = Intersect(Var("gg"), collection.mutable.Set.empty)
    val r2 = Intersect(collection.mutable.Set.empty, Var("gg"))
    val result = new SetDSL().interEval(r1)
    val result2 = new SetDSL().interEval(r2)
    result shouldBe collection.mutable.Set.empty
    result2 shouldBe collection.mutable.Set.empty
  }
}