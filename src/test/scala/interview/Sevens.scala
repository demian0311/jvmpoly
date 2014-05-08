package interview

import org.junit.Test

/**
 * Created by demian on 5/5/14.
 *
 * Rod has the solution to this for solving it without
 * using Strings
 *
 * http://pastebin.com/iwrpw16s
 *
 */
class Sevens7 {

  def filter7(numberIn: Int): Option[Integer] = {
    if(!numberIn.toString.contains("7")){
      Some(numberIn)
    } else{
      None
    }
  }


  @Test def test(){
    val noSevens = for {
      currNum <- (1 to 1000)
      noSevens <- filter7(currNum)
    } yield noSevens

    //val total = noSevens.sum()

    println("noSevens: " + noSevens)
    //println("total: " + total)
  }
}
