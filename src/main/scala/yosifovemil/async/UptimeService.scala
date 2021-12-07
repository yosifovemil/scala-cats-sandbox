package yosifovemil.async

import cats.Applicative
import cats.instances.list._
import cats.syntax.traverse._
import cats.syntax.functor._ // for map


class UptimeService[F[_]](client: UptimeClient[F])(implicit val appF: Applicative[F]) {
  def getTotalUptime(hostnames: List[String]): F[_] =
    hostnames.traverse(client.getUptime).map(_.sum)
}