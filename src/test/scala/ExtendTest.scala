import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExtendTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Extends"

  it should "Create a 2 class definition and extend a parent class to a child class" in {
    val someClass = ClassDef("someNewClass",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m8", Union(Insert(1), Insert(3)))).evalClassDef
    val antherClass = ClassDef("anotherClass",Field("public","ff"), Constructor(AssignField("ff", 90)),Method("public","m8",Intersect(Insert(1, 2), Insert(3, 2)))).evalClassDef
    val ext = Extends("someNewClass", "anotherClass").evalClass
    ext shouldBe "someNewClass"
    
  }
}