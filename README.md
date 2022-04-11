Homework 4 – Patryk Konieczny - patrykk2

Update Notes: This homework includes updates for branching and exceptions.
The tests that I added for this homework are TryTest, IFTest, ExceptionDefTest, CatchTest, and CatchErrorTest.
In order to use both languages, you have to import the languages. This can be done by using import SetDSL.SetOps.*
and import  SetDSL.ClassOps.* in the methods you plan to use them.
Additionally when testing all of the tests using sbt in the terminal, the CatchErrorTest causes some of the tests
to fail. When that test is isolated out of the rest of the tests they pass.



How to Download

Using Git Commands- The user can use git commands to clone the Program locally on their computer. If the user is using Mac OS then they can use the terminal, or if they are using Windows 10 then they can use PowerShell. Once opened, the user can use the commands cd <dir>, where <dir> is the directory that they would like to clone the project in. Then the user can clone the program using the command git clone https://github.com/PatrykKonieczny/CS474_HW1.git. This will clone the project into the users chosen directory.

Zip – The user has the option to download the file using the download zip option. This will download the file to the downloads directory of your computer. If you need to decompress the files from a zip, then right click on the file and select extract or unzip.

How to Run

Terminal commands – The user can run the program using terminal commands. They can do this by using cd <dir>, where <dir> is the name of the project directory. Then they can use sbt complie clean run/test to run the program.

InteliJ – The user can run the program through the InteliJ IDE.  Once the IDE is open, the user can select the Open option. From there the user can select project directory.  The user can then compile and run the program through the InteliJ IDE. To do so the user can click on the build option located on the menu bar. Then on the run option located on the menu bar. If a configuation is needed to run the program, then choose sbt Task.

Using the Language

In order to evaluate these following expressions use .eval at the end of the expression.

Assign(name : “String”, set : Set[Any]) => Set[Any]

The Assign expression creates a binding between a string and a set of type Set[Any]. This binding is stored using a map within the language. The first parameter takes a string, which is the key to the map, and the second parameter is a set, which is the value of the map. The user can nest operation within the second parameter if those operation return a set. The assign method returns the set that it is creating a binding for.

Insert( value: Any*)=> Set[Any]

The Insert expression creates a Set of objects and returns that set. The objects that are stored in that Set are passed as parameters by the user. The user can insert as many parameters as possible and of Any type.  Set returned by insert can only be bound is used within the Assign expression.
Delete(name: “String” , value: Any) => Set[Any]

The Delete expression finds a set that has a binding and deletes a value from that set. The first parameter is the name of the set that should have an element deleted, and the second parameter is value that should be deleted from the set. The set without the item is returned.

Union(set1: Set[Any], set2: Set[Any]) => Set[Any]

The Union expression takes two parameters that are of types Set[Any]. It returns the set that has elements from set1 and from set2.  Other expressions can be nested into Union if they return a set of type Set[Any].

Intersect (set1: Set[Any], set2: Set[Any]) => Set[Any]

The Intersect expression takes two parameters that are of type Set[Any]. It returns a set of elements that exist both in set1 and in set2. Other expressions can be nested into Intersect if they return a set of type Set[Any]..

SetDifference(set1: Set[Any], set2: Set[Any]) => Set[Any]

The SetDifference expression takes two parameters that are of Set[Any]. It returns the set of values in set1 that are not in set 2. Other expressions can be nested into SetDifference if they return a set of type Set[Any]..

SymmDifference( set1: Set[Any], set2: Set[Any]) => Set[Any]

The SymmDifference(Symmetrical Difference) expression takes two parameters of Set[Any]. It returns the set of values that are not in both set1 and in set2. Other expressions can be nested into SetDifference if they return a set of type Set[Any].

CartProduct(set1: Set[Any], set2: Set[Any])) => Set[Tuple(Any, Any)]

The CartPreduct(Cartesian Product) expression takes two parameters of Set[Any]. It returns the set of all possible order pairs between set1 and set2.

Macro(name: “String”, obj: Set[Any]) => Set[Any]

The Macro expression takes a parameter name and a parameter obj. The name parameter is a string that binds that name to the result of the expression that is passed through the obj parameter. Macro returns the set that is a result of the expression passed in the obj parameter. To assign the macro, use Var(name) in the second parameter of Assign. This will Assign the result of that Macro.
Scope(name : “String”, obj: Set[Any] ) => Set[Any]

The Scope expression takes a parameter name and obj. The name is a string that is the name of the created scope. The obj is the expressions that bound to that scope. These expressions return a Set[Any], then the expression binds the name of the scope to the set. The expression also returns a type of Set[Any]

In order to evaluate the following expression, use . checkItem after the expression.

Check(name: “String”, value:  Any) => Boolean

This expression takes a parameter name of type string and a value of type any. This expression checks if value exists in the set that is bounded to the name that is passed in. This returns true if it exists and false if it does not. 


Homework2– Patryk Konieczny  - Patrykk2
Using the Class Language
Field(accessType:String, name: String)

The Field method takes an access type as a string and the name of the field which is also a string. This returns a new field object. It is used in the ClassDef method to the create a new field to store in a class definition. This allows the Class Definition of an object to store the necessary data about a field.

Method(accessType:String, name: String, exp: SetOps)

The Method, method takes an access type as a string, the name of the method as a string and an expression that is a setOps expression.  This method returns a new method object. It is used in the ClassDef method to store all the relevant method data.

AssignField(name:String, item:Any)

The AssignField method takes a name string and an item of Any.  This method returns a map of the name and item. It binds the field to a value. It is used inside the constructor to create that mapping.

Constructor(AssignField(name:String, item:Any))

The Constructor method takes an AssignField Expression. This helps create a binding between a field and a value that is defined in the class.  This value is called once a class is instantiated. This implementation only takes one argument of AssignField.

ClassDef(name: String, Field, Constructor , Method)

The ClassDef method creates a new class definition. It takes a name of type string, a Field Method, a Constructor method, and a Method method. This implementation does not consider multiple methods and constructors and nested classes. This Method creates a binding between the name that was given and a new classDef object that holds the field, constructor, and method.

NewObject(name: String, variable : String)

The new NewObject creates a new instance of a class name and creates a binding to the variable. The parameters name and variable are of types string. This returns either the class that is bounded or nothing if it is already bound. This method calls the constructor of the class which created a binding between a field and value.

Extends(parentClass: String, childClass: String)

This Extends method creates a link between a parent and child class. The method takes two parameters a parent class name of type string and a child class name of type string. Both classes should be defined before they are extended. The method Maps a child class to the parent to keep records of which class has a parent.

InvokeMethod(className: String, methodName: String)

The InvokeMethod method executes a chosen method from a chosen class. This method takes a class name as a string and a method name as a string. The class name is the class that you want to invoke the method from, and the method name is the name of that method. In this implementation the class should be instantiated by using the NewObject method prior to using the InvokeMethod method. The class name that is passed into the method should be the variable that is bounded to the class when creating a new instance with NewObject. 

Homework3 

AbstractMethod(name)

This method takes a value name that is a string. This creates an instance of an abstract method. This function returns a method object with an empty expression. Evaluated with .evalMethod.

AbstractClassDef(name, Field, Constructor, Method*)

This method defines an abstract class. It takes a name parameter which is a string. It takes a Field class operation, a constructor operation, and Method operations. This returns an AbstractClass object. It can have both abstract and concrete methods but needs at least one abstract method. Evaluated with .evalAbstractClass.

InterfaceDecl(name, Field, AbstractMethod*)

This method declares an interface. It takes a name parameter which is a string, It takes a Field class operation, and AbstractMethod operations. This declares an interface and then return an interface object. Evaluated with .evalClass.

Implements(className, implementationName)

This method takes two parameters of type string. The class name is the name of the class that is implementing the interface that is passed in implementationName. This method returns a string of the interface being implemented. Evaluated with .evalClass

Homework 4 – patrykk2

Evaluated using .evalClass
ExceptionClassDef(name: String, field: Field(axs, name))

This method defines a new exception. It takes a name parameter, which is of type string, and a field parameter which is a Field expression(which takes a access type parameter and a name parameter and both are strings).

Evaluated using .eval
IF(condition: Check(), thenBlock: SetOps, elseBlock: SetOps)

The IF method takes a Boolean condtition. The only Boolean condition that is available in the SetOps library is Check, so that is the only expression that can be taken. The thenBlock and elseBlock parameters are of type set ops. The thenBlock set operation executes if the condition is true and the elseBlock condition executes if the condition is false.

ThrowException(name: String, message: String)

This method takes a name of an already defined exception, and a message that should be connected to that expression. This method throws a new exception and can only be thrown from inside the CatchException function. This method bypasses all expressions till a Catch expression is reached. If the catch exception is not reached an error occurs because the exception was not caught.

CatchException(name: String , expressions: SetOps*)

This method takes a name perameter of type string, and a list of set expressions of type SetOps. The name is the name of a defined Exception which can be thrown from within this expression. The list of expressions are any SetOps expressions that are evaluated with .eval.

Catch(name: String, expressions: SetOps*)

This method takes a parameter name of type string and a list of expressions of type SetOps. The name is the name of the exception that is being caught and the list of expressions are any SetOps expressions that are evaluated with .eval.

GetExceptionMessage(name: String, fieldName:String)

This method takes a parameter name of type string and a fieldName of type string. The name is of a thrown exception that you would like to get the message from. The field name is the name of the field when the exception was defined. This method returns the message of the exception in a Set. 
