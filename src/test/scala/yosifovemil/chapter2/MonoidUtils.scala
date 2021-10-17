package yosifovemil.chapter2

object MonoidUtils {

  def checkAssociativeLaw[A](x: A, y: A, z: A)
                            (implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
      m.combine(m.combine(x, y), z)
  }

  def checkIdentityLaw[A](x: A)
                         (implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
      (m.combine(m.empty, x) == x)
  }

}
