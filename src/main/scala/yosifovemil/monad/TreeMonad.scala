package yosifovemil.monad

import cats.Monad
import yosifovemil.Tree._
import yosifovemil.{Tree, Leaf, Branch}


object TreeMonad {
  implicit val treeMonad = new Monad[Tree] {
    def pure[A](a: A): Tree[A] = leaf(a)

    def flatMap[A, B](tree: Tree[A])(fn: A => Tree[B]): Tree[B] = tree match {
      case Leaf(a) => fn(a)
      case Branch(left, right) => branch(flatMap(left)(fn), flatMap(right)(fn))
    }

    def tailRecM[A,B](a: A)(fn: A => Tree[Either[A,B]]): Tree[B] =
      flatMap(fn(a)) {
        case Left(a) => tailRecM(a)(fn)
        case Right(b) => pure(b)
      }
  }
}