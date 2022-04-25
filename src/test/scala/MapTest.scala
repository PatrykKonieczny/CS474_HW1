import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class MapTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Map"

  it should "Map an optimization function to a partially evaluated expression" in {
    val s1:SetDSL.SetType = collection.mutable.Set(1,2,4)
    val s3:SetDSL.SetType = collection.mutable.Set("1",2, 90.9)
    Assign("x", Insert(1,2,4)).eval
    Assign("y", Insert()).eval
    Assign("z", Insert("1", 2 , 90.9)).eval
    val r1 = Union(Var("TT"),Var("y")).map(e => new SetDSL().unionEval(e))
    val r2 = Intersect(Var("TT"), Var("y")).map(e => new SetDSL().interEval(e))
    val r3 = SetDifference(Var("x"), Var("x")).map(e => new SetDSL().complimentEval(e))
    r1 shouldBe Var("TT")
    r2 shouldBe collection.mutable.Set.empty
    r3 shouldBe collection.mutable.Set.empty
    
  }
}