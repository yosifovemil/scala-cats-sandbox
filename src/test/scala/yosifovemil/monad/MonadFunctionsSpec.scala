package yosifovemil.monad

import cats.implicits._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util._
import yosifovemil.monad.MonadFunctions._


class MonadFunctionsSpec extends AnyFlatSpec with Matchers {
  "MonadFunctionsSpec.validateAdult" should "return success for age 18" in {
    validateAdult[Try](18) shouldEqual Success(18)
  }

  it should "return IllegalArgumentException for age 8" in {
    val error =
      intercept[IllegalArgumentException] {
        validateAdult[Try](8).get
      }
    error.getMessage() shouldEqual "Age too low!"
  }

  it should "return IllegalArgumentException for age -1 when using Either[Throwable, A]" in {
    type ExceptionOr[A] = Either[Throwable, A]
    val result: ExceptionOr[Int] = validateAdult[ExceptionOr](-1)
    result match {
      case Left(err: IllegalArgumentException) => err.getMessage shouldEqual "Age too low!"
      case _ => fail("")
    }
  }

  "MonadFunctions.foldRight" should "not throw StackOverflow error" in {
    val largeList: List[Long] = (1L to 7000L).toList
    foldRight(largeList, 0L)(_ + _)
  }

  "MonadFunctions.factorialWithWriter" should "reliably separate the results and logs of each run of the function" in {
    val expected = Vector(
      factorialWithWriter(5),
      factorialWithWriter(5)
    )

    val actual = Await.result(Future.sequence(Vector(
      Future(factorialWithWriter(5)),
      Future(factorialWithWriter(5))
    )), 5.seconds)

    println(actual)

    actual shouldBe expected
  }

  "MonadFunctions.findUsername" should "find username in DB" in new Scope {
    for {
      userId <- 1 to 2
    } yield findUsername(userId).run(testDb1) shouldBe(Some(s"user${userId}"))
  }

  "MonadFunctions.findPassword" should "return true when username/password is in DB" in new Scope {
    for {
      userId <- 1 to 2
    } yield checkPassword(s"user${userId}", s"power_${userId}_overwhelming").run(testDb1) shouldBe(true)
  }

  it should "return false when username/password is not in DB" in new Scope {
    checkPassword("user1", "power_2_overwhelming").run(testDb1) shouldBe(false)
  }

  "MonadFunctions.checkLogin" should "return true for existing username/password" in new Scope {
    checkLogin(1, "zerocool").run(db) shouldBe(true)
  }

  it should "return false for non existing username/password" in new Scope {
    checkLogin(4, "davinci").run(db) shouldBe(false)
  }

  trait Scope {
    val testDb1: Db = Db(
      usernames = Map(1 -> "user1", 2 -> "user2"),
      passwords = Map("user1" -> "power_1_overwhelming", "user2" -> "power_2_overwhelming")
    )

    val users = Map(
      1 -> "dade",
      2 -> "kate",
      3 -> "margo"
    )

    val passwords = Map(
      "dade"  -> "zerocool",
      "kate"  -> "acidburn",
      "margo" -> "secret"
    )

    val db = Db(users, passwords)
  }
}
