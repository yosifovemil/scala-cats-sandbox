package yosifovemil.monad

import cats.data.EitherT
import cats.implicits._
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class Transformers(powerLevels: Map[String, Int]) {
  private val levels: Map[String, Int] = powerLevels

  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(autobot: String): Response[Int] =
    levels.get(autobot) match {
      case Some(powerLevel) => EitherT.right(Future(powerLevel))
      case None => EitherT.left(Future(s"$autobot unreachable"))
    }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      ally1PowerLevel <- getPowerLevel(ally1)
      ally2PowerLevel <- getPowerLevel(ally2)
    } yield (ally1PowerLevel + ally2PowerLevel) > 15

  def tacticalReport(ally1: String, ally2: String): String =
    Await.result(canSpecialMove(ally1, ally2).value, 1.second) match {
      case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) => s"$ally1 and $ally2 need a recharge."
      case Left(err) => s"Comms error: $err"
    }
}