package yosifovemil.monoid

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.monoid.BooleanMonoid._
import yosifovemil.monoid.MonoidUtils._


class BooleanMonoidSpec extends AnyFlatSpec with Matchers {

  "andMonoid" should "obey the identity law" in new Scope{
    identityLaw(andMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(andMonoid) shouldBe(true)
  }

  "orMonoid" should "obey the identity law" in new Scope{
    identityLaw(orMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(orMonoid) shouldBe(true)
  }

  "xorMonoid" should "obey the identity law" in new Scope{
    identityLaw(xorMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(xorMonoid) shouldBe(true)
  }

  "xnorMonoid" should "obey the identity law" in new Scope{
    identityLaw(xnorMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(xnorMonoid) shouldBe(true)
  }

  trait Scope {
    val possibleValues: Seq[Boolean] = Seq(true, false)
    def cartesianProduct: Seq[(Boolean, Boolean, Boolean)] =
      for (
        arg1 <- possibleValues;
        arg2 <- possibleValues;
        arg3 <- possibleValues
      ) yield (arg1, arg2, arg3)

    def associativeLaw(monoid: Monoid[Boolean]): Boolean = {
      cartesianProduct.map {
        case (arg1, arg2, arg3) => checkAssociativeLaw(arg1, arg2, arg3)(monoid)
      }.forall(b => b)
    }

    def identityLaw(monoid: Monoid[Boolean]): Boolean = {
      possibleValues.map(
        checkIdentityLaw(_)(monoid)
      ).forall(b => b)
    }
  }

}
