package yosifovemil

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.CodecInstances._
import yosifovemil.Codec._

class CodecSpec extends AnyFlatSpec with Matchers {
  "Codec[Double].imap" should "create a Codec[Int] when given the correct functions" in {
    implicit val intCodec: Codec[Int] = doubleCodec.imap[Int](_.toInt, _.toDouble)

    encode(3) shouldBe "3.0"
    decode("4.9")(intCodec) shouldBe 4
  }

  "Codec[Box[Double]]" should "be able to use Codec[Double] to encode/decode Box[Double] values" in {
    implicit val boxDoubleCodec: Codec[Box[Double]] = boxCodec[Double]

    encode(1.3) shouldBe "1.3"
    decode("3.14")(boxDoubleCodec) shouldBe Box(3.14)
  }
}