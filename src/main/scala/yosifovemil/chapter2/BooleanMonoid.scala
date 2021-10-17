package yosifovemil.chapter2

object BooleanMonoid {
  implicit val andMonoid: Monoid[Boolean]  = new Monoid[Boolean] {
    override def combine(x:  Boolean, y:  Boolean): Boolean = x && y
    override def empty: Boolean = true
  }

  implicit val orMonoid: Monoid[Boolean]  = new Monoid[Boolean] {
    override def combine(x:  Boolean, y:  Boolean): Boolean = x || y
    override def empty: Boolean = false
  }

  implicit val xorMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def combine(x:  Boolean, y:  Boolean): Boolean = (x && !y) || (!x && y)
    override def empty: Boolean = false
  }

  implicit val xnorMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def combine(x:  Boolean, y:  Boolean): Boolean = (x || !y) && (!x || y)
    override def empty: Boolean = true
  }
}