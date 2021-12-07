package yosifovemil.async

import scala.concurrent.Future
import cats.Id

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}

// Async client for production
trait RealUptimeClient extends UptimeClient[Future] {
  def getUptime(hostname: String): Future[Int]
}

// sync client for testing
class TestUptimeClient(val hosts: Map[String, Int]) extends UptimeClient[Id] {
  def getUptime(hostname: String): Id[Int] =
    hosts.getOrElse(hostname, 0)
}