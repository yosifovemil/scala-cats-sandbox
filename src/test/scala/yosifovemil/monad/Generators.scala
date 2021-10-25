package yosifovemil.monad

import org.scalacheck.{Arbitrary, Gen}
import yosifovemil.{Tree, Branch, Leaf}
import yosifovemil.Tree._

object Generators {
  implicit val arbitraryInt: Gen[Int] = Gen.chooseNum(0, 100)

  implicit val arbitraryTree: Arbitrary[Tree[Int]] = Arbitrary{ treeGenerator(0) }

  private def treeGenerator(level: Int): Gen[Tree[Int]] ={
    if (level > 1000)
      leafGenerator
    else
      Gen.oneOf(leafGenerator, branchGenerator(level + 1))
}

  private def branchGenerator(level: Int): Gen[Tree[Int]] =
    for {
      left <- treeGenerator(level + 1)
      right <- treeGenerator(level + 1)
    } yield (branch(left, right))

  private def leafGenerator: Gen[Tree[Int]] =
    for {
      value <- arbitraryInt
    } yield (leaf(value))
}
