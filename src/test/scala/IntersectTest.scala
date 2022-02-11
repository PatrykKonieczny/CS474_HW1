import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class IntersectTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Intersect Operation"

  it should "Insert a intersection of setName and otherSet into someSetName" in {
    Assign("interSet", Intersect(Assign("setName", Insert(1,2,3,4)),Assign("otherSet",Insert(1,5,5)))).eval
    Check("interSet", 2).checkItem shouldBe false
    Check("deletedSet", 1).checkItem shouldBe true
  }
}