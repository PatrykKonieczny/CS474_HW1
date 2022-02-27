import SetDSL.ClassOps.{AssignField, ClassDef, Constructor, Field, Method}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ClassCreateTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import  SetDSL.ClassOps.*

  behavior of "New Object"

  it should "Create a new instance of class cOne" in {
    val someClass = ClassDef("cOne",Field("public","y"), Constructor(AssignField("y", "Value")), Method("public","m1", Union(Insert(1), Insert(3)))).evalClassDef
    NewObject("cOne", "someOtherClass").evalClass
    someClass.fields.fieldVal("y") shouldBe "Value"
  }
}
