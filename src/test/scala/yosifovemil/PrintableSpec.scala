package yosifovemil

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.Printable._
import yosifovemil.PrintableInstances.printableBox
import yosifovemil.PrintableSyntax._

class PrintableSpec extends AnyFlatSpec with Matchers {
  "Printable.format" should "format a Cat object correctly" in new Scope {
    val expected: String = catAsString
    val actual: String = format(cat)

    actual shouldEqual expected
  }

  "PrintableSyntax.format" should "format a Cat object correctly" in new Scope {
    val expected: String = catAsString
    val actual: String = PrintableOps(cat).format

    actual shouldEqual expected
  }

  "printableBox" should "work for Printable[String]" in {
    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          s"'${value}'"
      }

    format("hello") shouldEqual("'hello'")
    format(Box("hello world")) shouldEqual("'hello world'")
  }

  it should "work for Printable[Boolean]" in {
    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {
        def format(value: Boolean): String =
          if(value) "yes" else "no"
      }

    format(true) shouldEqual("yes")
    format(Box(true)) shouldEqual("yes")
  }

  trait Scope {
    val cat =  Cat("Cassini", 3, "orange")
    val catAsString = "Cassini is a 3 year-old orange cat"
  }
}
