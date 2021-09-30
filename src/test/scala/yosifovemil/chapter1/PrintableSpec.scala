package yosifovemil.chapter1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import yosifovemil.chapter1.PrintableSyntax._

class PrintableSpec extends AnyFlatSpec with Matchers {
  "Printable.format" should "format a Cat object correctly" in new Scope {
    val expected: String = catAsString
    val actual: String = Printable.format(cat)

    actual shouldEqual expected
  }

  "PrintableSyntax.format" should "format a Cat object correctly" in new Scope {
    val expected: String = catAsString
    val actual: String = PrintableOps(cat).format

    actual shouldEqual expected
  }

  trait Scope {
    val cat =  Cat("Cassini", 3, "orange")
    val catAsString = "Cassini is a 3 year-old orange cat"
  }
}
