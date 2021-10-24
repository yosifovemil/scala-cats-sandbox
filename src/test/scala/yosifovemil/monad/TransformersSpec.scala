package yosifovemil.monad

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt

class TransformersSpec extends AnyFlatSpec with Matchers {

  "Transformers.getPowerLevel" should "return the correct power level for a reachable autobot" in new Scope {
    getPowerLevel("Jazz") shouldEqual(Right(6))
    getPowerLevel("Bumblebee") shouldEqual(Right(8))
    getPowerLevel("Hot Rod") shouldEqual(Right(10))
  }

  it should "return an error string if the bot is unreachable" in new Scope {
    getPowerLevel("Zeta Prime") shouldEqual(Left(errorString("Zeta Prime")))
  }

  "Transformers.canSpecialMove" should "return true if the power levels exceed 15" in new Scope {
    canSpecialMove("Jazz", "Hot Rod") shouldEqual(Right(true))
    canSpecialMove("Bumblebee", "Hot Rod") shouldEqual(Right(true))
  }

  it should "return false if the power levels do not exceed 15" in new Scope {
    canSpecialMove("Jazz", "Bumblebee") shouldEqual(Right(false))
  }

  it should "return an error string if one of the autobots is unreachable" in new Scope {
    canSpecialMove("Jazz", "Optimus Prime") shouldEqual(Left(errorString("Optimus Prime")))
    canSpecialMove("Mirage" ,"Bumblebee") shouldEqual(Left(errorString("Mirage")))
  }

  "Transformers.tacticalReport" should "return the correct report" in new Scope {
    transformers.tacticalReport("Jazz", "Bumblebee") shouldEqual "Jazz and Bumblebee need a recharge."
    transformers.tacticalReport("Bumblebee", "Hot Rod") shouldEqual "Bumblebee and Hot Rod are ready to roll out!"
    transformers.tacticalReport("Jazz", "Ironhide") shouldEqual "Comms error: Ironhide unreachable"
  }

  trait Scope {
    val powerLevels = Map(
      "Jazz"      -> 6,
      "Bumblebee" -> 8,
      "Hot Rod"   -> 10
    )

    val transformers = new Transformers(powerLevels)

    def getPowerLevel(autobot: String): Either[String, Int] =
      runFuture(transformers.getPowerLevel(autobot).value)

    def canSpecialMove(autobot1: String, autobot2: String): Either[String, Boolean] =
      runFuture(transformers.canSpecialMove(autobot1, autobot2).value)

    def runFuture[A](future: Future[Either[String, A]]): Either[String, A] =
      Await.result(future, 1.second)

    def errorString(autobot: String) = s"$autobot unreachable"
  }
}

