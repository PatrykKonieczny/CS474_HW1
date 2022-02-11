import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class DeleteTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Delete Operation"

  it should "Delete an object from a set" in {
    Assign("deleteSet", Insert("a","b",1)).eval
    Delete("deleteSet", "b").eval
    Check("interSet", "b").checkItem shouldBe false
  }
}