import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ClassDefTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "ClassDef"

  it should "Create a new Class definiton of someCLass and store a field constructor and method" in {
    val someClass = ClassDef("someClass",Field("public","x"), Constructor(AssignField("x", 5)),Method("public","m1", Union(Insert(1), Insert(3)))).evalClassDef
    someClass.fields.fieldName shouldBe "x"
    someClass.methods.methodName shouldBe "m1"
  }
}