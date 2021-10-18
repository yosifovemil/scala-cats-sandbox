package yosifovemil

import cats.syntax.functor._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.TreeFunctor._     // for map


class TreeSpec extends AnyFlatSpec with Matchers {

  "TreeFunctor" should "obey the identity law for Branch" in new Scope{
    val expected: Tree[Int] = tree1
    val actual: Tree[Int] = tree1.map(a => a)

    actual shouldBe(expected)
  }

  it should "obey the identity law for Leaf" in new Scope{
    val expected: Tree[String] = tree2
    val actual: Tree[String] = tree2.map(a => a)

    actual shouldBe(expected)
  }

  it should "obey the composition law for Branch" in new Scope{
    def f: Int => Int = _ + 3
    def g: Int => Double = _.toDouble + 3.1

    val treeMappedInSequence: Tree[Double] = tree1.map(f).map(g)
    val treeMappedAtTheSameTime: Tree[Double] = tree1.map(a => g(f(a)))

    treeMappedInSequence shouldBe(treeMappedAtTheSameTime)
  }

  it should "obey the composition law for Leaf" in new Scope{
    def f: String => String = _ + "EFGH"
    def g: String => String = _.reverse

    val treeMappedInSequence: Tree[String] = tree2.map(f).map(g)
    val treeMappedAtTheSameTime: Tree[String] = tree2.map(a => g(f(a)))

    treeMappedInSequence shouldBe(treeMappedAtTheSameTime)
  }

  trait Scope {
    val tree1: Tree[Int] = Branch(
      Branch(
        Leaf(3),
        Branch(
          Leaf(4),
          Leaf(2)
        )
      ),
      Branch(
        Leaf(2),
        Leaf(5)
      )
    )

    val tree2: Tree[String] = Leaf("ABCD")
  }

}
