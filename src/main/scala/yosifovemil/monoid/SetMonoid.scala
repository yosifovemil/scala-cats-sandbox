package yosifovemil.monoid
import scala.collection.immutable.Set

object SetMonoid {
  implicit def unionMonoid[A]: Monoid[Set[A]]  = new Monoid[Set[A]] {
    override def combine(x:  Set[A], y:  Set[A]): Set[A] = x.union(y)
    override def empty: Set[A] = Set.empty[A]
  }

  /* Taken from the book, I did not think of that one :) */
  implicit def symDiffMonoid[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      def combine(a: Set[A], b: Set[A]): Set[A] =
        (a diff b) union (b diff a)
      def empty: Set[A] = Set.empty
    }
}
