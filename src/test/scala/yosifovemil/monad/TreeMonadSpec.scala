package yosifovemil.monad

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import yosifovemil.monad.Generators._
import yosifovemil.monad.TreeMonad._
import yosifovemil.Tree
import yosifovemil.Tree._


class TreeMonadSpec extends AnyFlatSpec with ScalaCheckPropertyChecks with Matchers {
  "TreeMonad" should "conform to the left identity law" in {
    forAll{ leafValue: Int => {
      def func: Int => Tree[Double] = x => leaf(x.toDouble)
      val tree = treeMonad.pure(leafValue)
      treeMonad.flatMap(tree)(func) shouldEqual func(leafValue)
    }}
  }

  it should "conform to the right identity law" in {
    forAll { tree: Tree[Int] => {
      treeMonad.flatMap(tree)(treeMonad.pure) shouldBe tree
    }}
  }

  it should "conform to the associativity law" in {
    forAll { tree: Tree[Int] => {
      def f: Int => Tree[Int] = x => leaf(x * 2)
      def g: Int => Tree[String] = x => leaf(x.toString)

      val treeF = treeMonad.flatMap(tree)(f)
      val treeG = treeMonad.flatMap(treeF)(g)

      val treeWithBoth = treeMonad.flatMap(tree)(x => treeMonad.flatMap(f(x))(g))

      treeG shouldBe treeWithBoth
    }}
  }
}
