import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class CartesianProductTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Cartesian Product Operation"

  it should "assign the cartesian product of 2 sets to a set" in {
    Assign("crossSet", CartProduct(Assign("oneSet", Insert(1 , 5)), Assign("twoSet",Insert(9, 2)))).eval
    Check("crossSet", (1,2)).checkItem shouldBe true
  }
}