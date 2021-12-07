package yosifovemil.mapreduce

import cats.Foldable.ops.toAllFoldableOps
import cats.{Foldable, Monad, Monoid}
import cats.syntax.semigroup._
import cats.instances.future._
import cats.instances.vector._
import cats.syntax.traverse._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MapReduce {
  def foldMap[A, B: Monoid](v: Vector[A])(f: A => B): B =
    v.map(f).foldLeft(Monoid[B].empty)(_ |+| _)

  def parallelFoldMap1[A, B: Monoid](v: Vector[A])(f: A => B): Future[B] = {
    val cpuCount: Int = Runtime.getRuntime.availableProcessors()
    val batchSize: Int = v.size / cpuCount

    val jobs: Future[Vector[B]] = v.grouped(batchSize).toVector.map( x =>
      Future(foldMap(x)(f))
    ).sequence

    for {
      batchResult <- jobs
    } yield (
      foldMap(batchResult)(b => b)
    )
  }

  def parallelFoldMap2[A, B: Monoid](v: Vector[A])(f: A => B): Future[B] = {
    val cpuCount: Int = Runtime.getRuntime.availableProcessors()
    val batchSize: Int = v.size / cpuCount

    v.grouped(batchSize)
      .toVector
      .traverse(group => Future(group.foldMap(f)))
      .map(_.combineAll)
  }
}