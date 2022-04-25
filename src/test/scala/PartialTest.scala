import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class PartialTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Partial Evaluation"

  it should "When a variable is undefined return the expression that is reduced to Set[any] | Expression" in {
    val s1:SetDSL.SetType = collection.mutable.Set(1,2,4)
    val s2:SetDSL.SetType = collection.mutable.Set("A","B","C")
    val s3:SetDSL.SetType = collection.mutable.Set("1",2, 90.9)
    val s4:SetDSL.SetType = collection.mutable.Set("apple","orange")
    val s5:SetDSL.SetType = collection.mutable.Set("elephant","bear")
    Assign("x", Insert(1,2,4)).eval
    Assign("y", Insert("A", "B", "C")).eval
    Assign("z", Insert("1", 2 , 90.9)).eval
    Assign("s", Insert("apple","orange")).eval
    Assign("w", Insert("elephant","bear")).eval
    val r1 = Union(Var("x"),Var("a")).eval
    val r2 =Intersect(Var("b"), Var("y")).eval
    val r3 = SetDifference(Var("z"), Var("c")).eval
    val r4 = SymmDifference(Var("d"), Var("s")).eval
    val r5 = CartProduct(Var("w"), Var("ff")).eval
    val r6 = IF(Check("x", 1), Union(Var("p"), Var("w")), Union(Var("z"), Var("y"))).eval
    r1 shouldBe Union(s1, Var("a"))
    r2 shouldBe Intersect(Var("b"), s2)
    r3 shouldBe SetDifference(s3, Var("c"))
    r4 shouldBe SymmDifference(Var("d"), s4)
    r5 shouldBe CartProduct(s5, Var("ff"))
    r6 shouldBe Union(Var("p"), s5)
  }
}