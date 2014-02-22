package com.neidetcher.caseclasses

import org.junit.Test
import org.junit.Assert._

class PatternMatching {

  @Test def matchConstants(){
    assertEquals("this is a one", 1 match {
      case 0 => "this is a zero"
      case 1 => "this is a one"
      case _ => "dunno"
    })

    assertEquals(1, "one" match{
      case "zero" => 0
      case "one"  => 1
      case _      => 0
    })

    assertEquals("hello world", "hello" match {
      case something => something + " world"
    })
  }

  @Test def wildcards(){
    case class Fruit(name: String, color: String)
    val apple = Fruit("apple", "green")
    val banana = Fruit("banana", "yellow")
    val cherry = Fruit("cherry", "red")

    val result = apple match{
      case Fruit(name, "green") => "found a green fruit named: " + name
      case Fruit(_, "yellow")   => "found a yellow fruit, don't care about the name"
      case _                    => "couldn't find it"
    }

    assertEquals("found a green fruit named: apple", result)
  }
}
