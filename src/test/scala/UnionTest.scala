import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class UnionTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Union Operation"

  it should "Insert a set of items 1,2,4 into setName an a, b in other set and assign unionSet to their union" in {
    Assign("unionSet", Union(Assign("setName", Insert(1,2,3,4)),Assign("otherSet",Insert("a","b")))).eval
    Check("unionSet", "a").checkItem shouldBe true
    Check("unionSet", 2).checkItem shouldBe true
  }
}
