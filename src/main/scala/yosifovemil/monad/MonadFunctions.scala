package yosifovemil.monad

import cats.{MonadError, Eval}
import cats.data.{Writer, Reader}
import cats.syntax.applicative._
import cats.instances.vector._
import cats.syntax.writer._

object MonadFunctions {
  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] ={
    val success = me.pure(age)
    me.ensure(success)(new IllegalArgumentException("Age too low!"))(_ >= 18)
  }

  /**
   *  Using Eval to make foldRight stack safe
   */
  def foldRightEval[A, B](as: List[A], acc: Eval[B])
                         (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil =>
        acc
    }

  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    foldRightEval(as, Eval.now(acc)) { (a, b) =>
      b.map(fn(a, _))
    }.value

  def slowly[A](body: => A): A =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  type Logged[A] = Writer[Vector[String], A]

  def factorialWithWriter(n: Int): Logged[Int] = {
    for {
      ans <- if (n == 0)
        1.pure[Logged]
      else {
        slowly(factorialWithWriter(n - 1).map(_ * n))
      }
      _ <- Vector(s"fact $n $ans").tell
     } yield ans
  }

  final case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db => db.usernames.get(userId))

  def checkPassword(
    username: String,
    password: String
  ): DbReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(
    userId: Int,
    password: String
  ): DbReader[Boolean] ={
    for {
      username <- findUsername(userId)
      approved <- username.map( user => checkPassword(user , password)).getOrElse(false.pure[DbReader])
    } yield approved
  }
}