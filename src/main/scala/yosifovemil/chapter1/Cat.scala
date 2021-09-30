package yosifovemil.chapter1

import cats._
import cats.implicits.catsSyntaxEq
import cats.instances.int._
import cats.instances.string._
import yosifovemil.chapter1.PrintableInstances._

final case class Cat(name: String, age: Int, color: String)

object Cat {
  // Printable instance
  implicit val printableCat: Printable[Cat] =
    new Printable[Cat] {
      def format(a: Cat): String =
        s"${Printable.format(a.name)} is a ${Printable.format(a.age)} year-old ${Printable.format(a.color)} cat"
    }

  // Show instance
  implicit val catShow: Show[Cat] = Show.show(
    cat => s"${cat.name} is a ${cat.age} year-old ${cat.color} cat"
  )

  // Eq instance
  implicit val eqCat: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      cat1.name === cat2.name &&
        cat1.age === cat2.age &&
        cat1.color === cat2.color
    }
}