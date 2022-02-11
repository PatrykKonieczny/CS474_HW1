import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Insets


class MacroTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*

  behavior of "Macro Operation"

  it should "allow for an expression to be represented by a string" in {
    Assign("someSet", Insert(1,5,"c")).eval
    Macro("someOperation", Delete("someSet", 1)).eval
    Assign("deletedSet", Var("someOperation")).eval
    Check("deletedSet", 1).checkItem shouldBe false
  }
}