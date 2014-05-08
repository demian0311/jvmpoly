package idiom

import org.junit.Test
import org.junit.Assert._

class ExpressionsOverStatements {

  val x = 10

  @Test def statements(){
    var a: String = null

    if(x > 0){
      a = "pos"
    } else {
      a = "neg"
    }

    assertEquals("pos", a)
  }

  @Test def expression() {
    val a = if(x > 0) "pos" else "neg"
    assertEquals("pos", a)
  }

  @Test def javaStyle(){
    def status(age: Int): String = {
      var disposition = "ADULT"

      if(age <= 18){
        disposition = "MINOR"
      } else if (age >= 65){
        disposition = "RETIREE"
      }

      disposition
    }

    assertEquals("MINOR", status(14))
  }

}
