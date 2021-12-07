package yosifovemil.mapreduce
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import yosifovemil.mapreduce.MapReduce._
import cats.implicits._


class MapReduceSpec extends AnyFlatSpec with Matchers {
  "MapReduce.parallelFoldMap" should "work" in {
    parallelFoldMap1((1 to 50).toVector)(x => {
      x - 1
    })
  }
}
