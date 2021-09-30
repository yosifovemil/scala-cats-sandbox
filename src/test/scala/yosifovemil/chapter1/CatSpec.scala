package yosifovemil.chapter1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import cats._

class CatSpec extends AnyFlatSpec with Matchers {
  "Cat.catShow.show" should "create the correct instance for Catt " in new Scope {

    val expected: String = cassiniCatAsString
    val actual: String = Cat.catShow.show(cassiniCat)

    actual shouldEqual(expected)
  }

  "Cat.eqCat" should "evaluate to true when comparing a Cat object with itself" in new Scope {
    val expected: Boolean = true
    val actual: Boolean = cat1 === cat1

    actual shouldEqual expected
  }

  it should "evaluate to false when comparing the different Cat object" in new Scope {
    val expected: Boolean = false
    val actual: Boolean = cat1 === cat2

    actual shouldEqual expected
  }

  it should "evaluate to true when comparing Option[Cat](cat1) with an equivalent object" in new Scope {
    val expected: Boolean = true
    val actual: Boolean = optionCat1 === Option(cat1)

    actual shouldEqual expected
  }

  it should "evaluate to false when comparing Option[Cat](cat1) with None Cat object" in new Scope {
    val expected: Boolean = false
    val actual: Boolean = optionCat1 === optionCat2

    actual shouldEqual expected
  }

  trait Scope {
    val cassiniCat =  Cat("Cassini", 3, "orange")
    val cassiniCatAsString = "Cassini is a 3 year-old orange cat"

    val cat1 = Cat("Garfield",   38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")

    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty[Cat]
  }
}
