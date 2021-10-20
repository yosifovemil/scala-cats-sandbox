package yosifovemil.monad
import cats.data.State
import cats.implicits._


object PostOrderCalculator {
  type CalcState[A] = State[List[Int], A]

  def operator(func: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case (a::b::nil) => {
        val result = func(a,b)
        (List(result), result)
      }
      case stack => throw new Exception(s"Bad input ${stack}")
    }

  def operand(num: String): CalcState[Int] =
    State[List[Int], Int] {
      stack => {
        val result = num.toInt
        (stack :+ result, result)
      }
    }

  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num)
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState])(
      (acc, sym) => acc.flatMap(_ => evalOne(sym))
    )
}