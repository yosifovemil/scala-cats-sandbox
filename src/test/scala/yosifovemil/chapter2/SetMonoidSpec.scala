package yosifovemil.chapter2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.chapter2.SetMonoid._
import yosifovemil.chapter2.MonoidUtils._


class SetMonoidSpec extends AnyFlatSpec with Matchers {

  "unionMonoid" should "obey the identity law" in new Scope{
    identityLaw(unionMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(unionMonoid) shouldBe(true)
  }

  "symDiffMonoid" should "obey the identity law" in new Scope{
    identityLaw(symDiffMonoid) shouldBe(true)
  }

  it should "obey the associative law" in new Scope{
    associativeLaw(symDiffMonoid) shouldBe(true)
  }

  trait Scope {
    val set1: Set[Int] = Set(1,2,3)
    val set2: Set[Int] = Set(3,4,5)
    val set3: Set[Int] = Set(4,7,8)

    def associativeLaw(monoid: Monoid[Set[Int]]): Boolean = {
      checkAssociativeLaw(set1, set2, set3)(monoid)
    }

    def identityLaw(monoid: Monoid[Set[Int]]): Boolean = {
      checkIdentityLaw(set1)(monoid)
    }
  }

}
