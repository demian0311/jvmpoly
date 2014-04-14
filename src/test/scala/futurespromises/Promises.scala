package futurespromises

import org.junit.Test
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * http://danielwestheide.com/blog/2013/01/16/the-neophytes-guide-to-scala-part-9-promises-and-futures-in-practice.html
 */
class Promises {

  @Test def foo(){
    val f: Future[String] = Future{"Hello world!"}
    println("f: " + f)
  }

  import concurrent.Promise
  case class TaxCut(reduction: Int)
  val taxcut = Promise[TaxCut]()
  val taxcut2: Promise[TaxCut] = Promise()

  @Test def bar(){
    val taxcutF: Future[TaxCut] = taxcut.future
    println("taxcutF: " + taxcut2)
  }

  object Government {
    def redeemCampaignPledge(): Future[TaxCut] = {
      val p = Promise[TaxCut]()

      Future{
        println("Starting the new legislative period.")
        Thread.sleep(2000)
        p.success(TaxCut(20))
        println("We reduced the taxes!  You must reelect us!!!!!")
      }

      p.future
    }
  }

}
