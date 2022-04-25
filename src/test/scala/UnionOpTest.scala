import SetDSL.SetOps.Var
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class UnionOpTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Union optimization functions that was implmented"

  it should "Return a empty set when made in " in {
    val s1:SetDSL.SetType = collection.mutable.Set(1,2,4)
    Assign("gg", Insert(1,2,4)).eval
    val r1 = Union(Var("xyz"), collection.mutable.Set.empty)
    val r2 = Union(collection.mutable.Set.empty, Var("xyz"))
    val r3 = Union(Var("gg"), collection.mutable.Set.empty)
    val r4 = Union(collection.mutable.Set.empty, Var("gg"))
    val result = new SetDSL().unionEval(r1)
    val result2 = new SetDSL().unionEval(r2)
    val result3 = new SetDSL().unionEval(r3)
    val result4 = new SetDSL().unionEval(r4)
    result shouldBe Var("xyz")
    result2 shouldBe Var("xyz")
    result3 shouldBe Var("gg")
    result4 shouldBe Var("gg")
  }
}