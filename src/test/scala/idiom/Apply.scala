package idiom

import org.junit.Test
import org.junit.Assert._


/*
def down(str: String) = {
  str.toLow
}
*/


class Apply {

  class Vegetable(val name: String, val color: String)

  object Vegetable{
    def unapply(v: Vegetable) = {
      new Some((v.name, v.color))
    }

    def unapply(s: String) = {
      val splitString = s.split('|')
      if(splitString.length != 2){
        None
      } else {
        Some((splitString(0), splitString(1)))
      }
    }
  }

  @Test def extractors(){
    def up(str: String) = {str.toUpperCase()}
    //up("hello")

    assertEquals("HELLO", up("hello"))

    val result: String = new Vegetable("corn", "yellow") match {
      case Vegetable(name, color) => s"got some $color $name"
      case _ => "must not be a vegetable"
    }
    assertEquals("got some yellow corn", result)
  }

  @Test def extractorsForParsing() {
    "spinach|green" match {
      case Vegetable(name, color) => {
        assertEquals("spinach", name)
        assertEquals("green", color)
      }
      case _ => fail("didn't create a Vegetable")
    }
  }

}
