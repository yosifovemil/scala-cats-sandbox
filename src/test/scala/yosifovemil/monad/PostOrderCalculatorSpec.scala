package yosifovemil.monad

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.monad.PostOrderCalculator._


class PostOrderCalculatorSpec extends AnyFlatSpec with Matchers {
  "PostOrderCalculator.calcAll" should "calculate the correct result" in {
    val multistageProgram = evalAll(List("1", "2", "+", "3", "*"))
    multistageProgram.runA(Nil).value shouldEqual(9)
  }
}
