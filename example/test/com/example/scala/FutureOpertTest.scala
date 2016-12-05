package com.example.scala

import org.specs2.mutable.Specification

import scala.concurrent.Future

/**
  * Created by suxin on 16-12-1.
  */
class FutureOpertTest extends Specification {

  "FutureOpertTest" should {
    "main" in {

      implicit val ec = play.api.libs.concurrent.Execution.Implicits.defaultContext

      for (
        a <- Future {
          println("a")
        };
        b <- Future {
          println("b")
        };
        c <- Future {
          println("c")
        };
        d <- Future {
          println("d")
        }
      ) yield d
      ok
    }

    "future.sequence" in {

      implicit val ec = play.api.libs.concurrent.Execution.Implicits.defaultContext
      //测试Future的转换
      /*val listOfFutures = List.fill(10) {
        val fm = Future {
          100
        }.mapTo[Int]
        fm
      }*/
      val listOfFutures: List[Future[Int]] = Nil
      val futureList = Future.sequence(listOfFutures)
      //异步执行
      futureList.map { s =>
        val sum = s.sum
        println("List sum = " + sum)
        sum
      }
      val seqOfFutures = Seq(Future {
        111
      }.mapTo[Int], Future(22).mapTo[Int])
      val futureSeq = Future.sequence(seqOfFutures)
      futureSeq.map { s =>
        val sum = s.sum
        println("SeqSum = " + sum)
        sum
      }
      println("是否异步")
      ok
    }

    "future.traverse" in {

      //      implicit val ec = play.api.libs.concurrent.Execution.Implicits.defaultContext
      import scala.concurrent.ExecutionContext.Implicits.global

      val listInt = List.fill(2)(1, 2, 3, 4, 5, 5)
      //Seq转换为Future
      val futureList = Future.traverse(listInt) { a => Future {
        new String("" + a)
      }.mapTo[String]
      }
      futureList.map { a =>
        val c = a.map { b =>
          println(b)
          1
        }
        c.map { d => println(d) }
      }
      ok
    }

    "future pr" in {
      import scala.concurrent.ExecutionContext.Implicits.global

      Future {
        println("Hello" + "World")
      }

      Future {
        println("Hello" + "World" + "=============")
      }
      ok
    }

    "future.map" in {
      import scala.concurrent.ExecutionContext.Implicits.global

      val f1 = Future {
        "Hello" + "World"
      }
      val f2 = f1 map { x =>
        x.length
      }
      f2 foreach println
      ok
    }

    "future.foreach" in {
      import scala.concurrent.ExecutionContext.Implicits.global

      val future = Future {
        throw new InterruptedException
      }

      val future1 = Future {
        "Hello" + "World" + "=================="
      }

      future onFailure {
        case msg => println(msg)
      }

      future1 onSuccess {
        case msg => println("==================" + msg )
      }


      future foreach println
      future1 foreach println
      ok
    }
  }
}
