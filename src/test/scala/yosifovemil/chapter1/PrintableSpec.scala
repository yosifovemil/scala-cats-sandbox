package yosifovemil.chapter1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import yosifovemil.chapter1.PrintableSyntax._
import yosifovemil.chapter1.Printable._

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

  "Printable.contramap" should "work for Printable[String]" in {
    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          s"'${value}'"
      }

    format("hello") shouldEqual("'hello'")

    implicit val boxStringPrintable: Printable[Box[String]] =
      stringPrintable.contramap(b => b.value)




    format(Box("hello world")) shouldEqual("'hello world'")
  }

  it should "work for Printable[Boolean]" in {
    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {
        def format(value: Boolean): String =
          if(value) "yes" else "no"
      }
    format(true) shouldEqual("yes")

    implicit val boxBooleanPrintable: Printable[Box[Boolean]] =
      booleanPrintable.contramap(b => b.value)

    format(Box(true)) shouldEqual("yes")
  }

  trait Scope {
    val cat =  Cat("Cassini", 3, "orange")
    val catAsString = "Cassini is a 3 year-old orange cat"
  }
}
