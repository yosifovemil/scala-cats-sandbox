package yosifovemil

import scala.math.Numeric


object ListFunctions {
  def map[A, B](l: List[A], f: A => B): List[B] =
    l.foldRight(List.empty[B])((a, acc) => f(a) :: acc)

  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] =
    l.foldRight(List.empty[B])((a, acc) => f(a) ++ acc)

  def filter[A](l: List[A])(f: A => Boolean): List[A] =
    l.foldRight(List.empty[A])((a, acc) => if (f(a)) acc :: a else acc)

  def sumWithNumeric[A](l: List[A])(implicit numeric: Numeric[A]): A =
    l.foldLeft(numeric.zero)(numeric.plus)
}

