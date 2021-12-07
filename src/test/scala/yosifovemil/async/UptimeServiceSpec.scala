package yosifovemil.async
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UptimeServiceSpec extends AnyFlatSpec with Matchers {

  "UptimeService.getTotalUptime" should "calculate total uptime correctly" in {
    val hosts    = Map("host1" -> 10, "host2" -> 6)
    val client   = new TestUptimeClient(hosts)
    val service  = new UptimeService(client)

    val actual   = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum

    actual shouldEqual expected
  }
}
