package idiom

import org.junit.Test
import org.junit.Assert._

class Collections {
  val vegetables = Vector("asparagus", "broccoli", "cabbage")

  @Test def usedWrong(){
    var hasCabbage: Boolean = false
    for(i <- 0 until vegetables.size){
      if(vegetables(i) == "cabbage"){
        hasCabbage = true
      }
    }

    assertTrue(hasCabbage)
  }

  @Test def usedKindaWrong(){
    var hasCabbage: Boolean = false
    vegetables.foreach{ curr =>
      if(curr == "cabbage"){
        hasCabbage = true
      }
    }

    assertTrue(hasCabbage)
  }

  @Test def usedRight1(){
    val hasCabbage = vegetables.find(_ == "cabbage") match {
      case Some(_) => true
      case None => false
    }

    assertTrue(hasCabbage)
  }


}
