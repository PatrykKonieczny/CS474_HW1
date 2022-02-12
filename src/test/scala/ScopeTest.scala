import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class ScopeTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Scope operation"

  it should "allow for an expression to be represented by a string" in {
    Scope("someScope", Assign("thisSet", Insert(1,5,"c"))).eval
    Check("someScope", "c").checkItem shouldBe true
  }
}