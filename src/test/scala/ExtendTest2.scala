import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExtendTest2 extends AnyFlatSpec with Matchers {

  import SetDSL.SetOps.*
  import SetDSL.ClassOps.*

  behavior of "Extends in relation to abstract classes and interfaces"

  it should "Test how classes can extend each other and interface extension" in {
    val someClass = ClassDef("someNewClass",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m8", Union(Insert(1), Insert(3)))).evalClassDef
    val someOtherClass = ClassDef("someOtherClass",Field("public","f"), Constructor(AssignField("f", 90)),Method("public","m5", Union(Insert(1), Insert(3)))).evalClassDef
    val absClass = AbstractClassDef("absClass", Field("public", "ff"), Constructor(AssignField("ff", 98)), AbstractMethod("m7")).evalAbstractClass
    val otherAbstract = AbstractClassDef("otherAbstract", Field("public", "ff"), Constructor(AssignField("ff", 98)), Method("public","m10", Insert(1,2,3)), AbstractMethod("m8")).evalAbstractClass
    val anInterface = InterfaceDecl("anInterface", Field("public", "fff"), AbstractMethod("m2"), AbstractMethod("m4")).evalClass
    val otherInterface = InterfaceDecl("otherInterface", Field("public", "fff"), AbstractMethod("m2"), AbstractMethod("m5")).evalClass

    val ext = Extends("anInterface", "otherInterface").evalClass
    Extends("someNewClass", "otherAbstract").evalClass
    NewObject("someNewClass","x").evalClass
    val meth = InvokeMethod("x","m8").evalClass

    meth shouldBe Union(Insert(1), Insert(3)).eval
    the [Error] thrownBy Extends("someNewClass", "anInterface").evalClass should have message "Interface cannot extend a concrete Class"
    the [Error] thrownBy Extends("someNewClass", "someNewClass").evalClass should have message "Class cannot extend itself"
    the [Error] thrownBy Extends("anInterface", "someNewClass").evalClass should have message "Interface cannot extend an abstract Class or an concrete Class"
    the [Error] thrownBy Extends("anInterface", "absClass").evalClass should have message "Interface cannot extend an abstract Class or an concrete Class"
    the [Error] thrownBy Extends("someNewClass", "absClass").evalClass should have message "Class does implement extended abstract method. Both classes should be abstract"
  }
}