package yosifovemil.chapter1

trait Printable[A] { self =>
  def format(a: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      def format(value: B): String =
        self.format(func(value))
    }
}

object PrintableInstances {
  implicit val printableString: Printable[String] =
    new Printable[String] {
      def format(a: String): String = a
    }

  implicit val printableInt: Printable[Int] =
    new Printable[Int] {
      def format(a: Int): String = a.toString
    }
}

object Printable {
  def format[A](a: A)(implicit printable: Printable[A]): String =
    printable.format(a)

  def print[A](a: A)(implicit printable: Printable[A]): Unit =
    println(format(a)(printable))
}

object PrintableSyntax {
  implicit class PrintableOps[A](get: A) {
    def format(implicit printable: Printable[A]): String =
      printable.format(get)

    def print(implicit printable: Printable[A]): Unit =
      println(printable.format(get))
  }
}

final case class Box[A](value: A)