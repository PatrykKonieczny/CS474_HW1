

object SetDSL:

  enum SetOps:
    case Value(input: Int)
    case Var(input: String)
    case Variable(input: String)
    case Insert
    case Delete
    case Intersect
    case SetDifference
    case SymmDifference
    case CartProduct




  @main def hello : Unit = {
    import SetDSL.*
    println("Hello")
  }

