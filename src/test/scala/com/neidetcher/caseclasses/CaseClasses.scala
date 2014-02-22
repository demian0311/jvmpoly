package com.neidetcher.caseclasses

import org.junit.Test
import org.junit.Assert._

class CaseClasses {

  sealed abstract class Plant

  case class Fruit(name: String) extends Plant
  case class Vegetable(name: String) extends Plant

  val apple: Plant = Fruit("apple")

  // TODO: add stuff from https://github.com/demian0311/towards_idiomatic_scala/blob/master/towards_idiomatic_scala/worksheets/case_classes_and_matchers_2/case_classes.sc

  @Test def findOutTypeTheWrongWay(){

    val result = if (apple.isInstanceOf[Fruit]) {
      val fruit: Fruit = apple.asInstanceOf[Fruit]
      "found a fruit: " + fruit.name
    } else if (apple.isInstanceOf[Vegetable]) {
      "didn't expect a veggie"
    } else {
      throw new MatchError(
        "should've used a matcher")
    }

    assertEquals("found a fruit: apple", result)
  }

  @Test def findOutTypeTheIdiomaticWay(){
    val result = apple match {
      case Fruit(name)  => "found a fruit: " + name
      case Vegetable(_) => "didn't expect a veggie"
    }

    assertEquals("found a fruit: apple", result)
  }
}
