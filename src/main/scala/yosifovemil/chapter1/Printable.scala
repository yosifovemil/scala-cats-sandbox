package yosifovemil.chapter1

trait Printable[A] {
  def format(a: A): String
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