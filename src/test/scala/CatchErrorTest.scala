import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CatchErrorTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "When an error is not caught"

  it should "Throw an error is a error that is thown is not caught" in {
    ExceptionClassDef("Error", Field("public", "Reason")).evalClass
    val exp = CatchException("Error", Assign("val", Insert(1,2,3)), Union(Insert(1,2,3), Insert("a")), IF(Check("val", 4), Assign("x", Insert("A", "B", "C")),
        ThrowException("Error", "Check Failed")),
      Assign("var", Insert(1,2,3))
    )

    //the [Error] thrownBy exp.eval should have message "Exception not caught"

  }
}