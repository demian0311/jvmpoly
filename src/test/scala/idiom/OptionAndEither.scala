package idiom

import org.junit.Test
import org.junit.Assert._

class OptionAndEither {

  @Test def monadPreview(){
    val arr: List[List[Option[Int]]] = List(
      List(Some(201), Some(113), None),
      List(Some(761), Some(942), Some(683)),
      List(None, Some(103), Some(824)))

    def findEven(arr: List[List[Option[Int]]]): List[Int] =
      for {
        row <- arr // outer List is a monad
        cell <- row // inner List is a monad
        cellValue <- cell // Option is a monad
        if (cellValue % 2 == 0) // filter
      } yield cellValue

    assertEquals(List(942, 824), findEven(arr))
  }

  case class User(id: Integer, name: String)

  def findUserById(id: Int): Either[String, User] = {
    id match{
      case 1 => Right(User(1, "admin"))
      case _ => Left("couldn't find user")
    }
  }

  @Test def either(){
    assertEquals(Left("couldn't find user"), findUserById(2))
  }

  @Test def eitherWrongWay(){
    val result = findUserById(1)
    val response = if(result.isRight){
      "found a user: " + result.right.get.name
    } else if (result.isLeft){
      "error: " + result.left
    }

    assertEquals("found a user: admin", response)
  }

  @Test def eitherIdiomatic(){
    val response = findUserById(1) match {
       case Right(u) => "found a user: " + u.name
       case Left(m) => "error: " + m
    }

    assertEquals("found a user: admin", response)
  }









}
