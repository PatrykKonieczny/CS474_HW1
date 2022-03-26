import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AbstractClassTest extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Creating a definition of an Abstract Class"

  it should "Create a new abstract class definitioin with an abstract method" in {
    val someAbstractClass = AbstractClassDef("someAbstract", Field("public","x"),Constructor(AssignField("x", 5)),Method("public","m1", Union(Insert(1), Insert(3))),AbstractMethod("m2")).evalAbstractClass
    val methods = someAbstractClass.methods
    val alikeMethods: collection.mutable.Set[String] = collection.mutable.Set.empty
    methods.foreach(u => alikeMethods += u.methodName)
    someAbstractClass.fields.fieldName shouldBe "x"
    alikeMethods.contains("m2") shouldBe true
    alikeMethods.contains("m2") shouldBe true
    the [Error] thrownBy AbstractClassDef("newAbstractClass", Field("public","x"),Constructor(AssignField("x", 5)),Method("public","m1", Union(Insert(1), Insert(3)))).evalAbstractClass should have message "Abstract method needed"
  }
}
