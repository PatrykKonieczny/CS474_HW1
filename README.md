Homework 2 – Patryk Konieczny - patrykk2

Update Notes: I built upon my homework one implemtation in order to include class operations.
The tests files that I added for my class operations are: ClassCreateTest, ClassDefTest, ExtendTest, InvokeMethodTest, OverwriteTest.
In order to use both languages, you have to import the languages. This can be done by using import SetDSL.SetOps.*
and import  SetDSL.ClassOps.* in the methods you plan to use them. The deliverables that I was failed to implment was 
multiple parameters in constructors and methods.



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


 
