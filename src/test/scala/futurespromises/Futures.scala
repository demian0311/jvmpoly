package futurespromises

import scala.util.{Failure, Success, Random, Try}
import org.junit.{Assert, Test}
import scala.concurrent.Future
import util.Timer

// This is needed for the Future to work
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This is me getting a better understanding of futures by walking thru:
 * http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html
 */
class Futures extends Assert{

  // type aliases
  type CoffeeBeans = String
  type GroundCoffee = String
  case class Water(temperature: Int)
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future{
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")

    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future{
    println("milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")

    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future{
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  case class GrindingException(msg: String) extends Exception(msg)
  case class FrothingException(msg: String) extends Exception(msg)
  case class WaterBoilingException(msg: String) extends Exception(msg)
  case class BrewingException(msg: String) extends Exception(msg)

  @Test def callbacks(){
    grind("arabica beans").onSuccess{ case ground =>
      println("okay, got my ground coffee")
      Assert.fail("this doesn't seem to execute, hmmm")
    }
  }

  @Test def callbacksWithMatcher(){
    grind("baked beans").onComplete {
      case Success(ground) => println(s"got my $ground")
      case Failure(ex) => println("This grinder needs a replacement, seriously!")
    }
  }

  @Test def mappingTheFuture(){
    val temperatureOkay: Future[Boolean] = heatWater(Water(25)).map { water =>
      println("we're in the future!")
      (80 to 85).contains(water.temperature)
    }

    println("temperatureOkay: " + temperatureOkay)
  }

  def temperatureOkay(water: Water): Future[Boolean] = Future {
    (80 to 85).contains(water.temperature)
  }

  @Test def testTemperatureOkay(){
    val nestedFuture: Future[Future[Boolean]] = heatWater(Water(25)).map{
      water => temperatureOkay(water)
    }
    println("nestedFuture: " + nestedFuture)

    // flatMap makes it so you don't need the additional wrapping
    val flatFuture: Future[Boolean] = heatWater(Water(25)).flatMap{
      water => temperatureOkay(water)
    }
    println("flatFuture: " + flatFuture)
  }

  @Test def forComprehensions(){
    val acceptable: Future[Boolean] = for{
      heatedWater <- heatWater(Water(25))
      okay <- temperatureOkay(heatedWater)
    } yield okay

    println("acceptable: " + acceptable)
  }

  @Test def prepareCappuccinoSequentially(){
    val timer = new Timer("prepareCappuccinoSequentially")

    val foo: Future[Cappuccino] = for {
      ground <- grind("arabica beans")
      water <- heatWater(Water(20))
      foam <- frothMilk("milk")
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)

    println(timer) // 295ms
  }

  @Test def prepareCappuccinoParallel(){
    val timer = new Timer("prepareCappuccinoParallel")

    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")

    for{
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)

    println(timer) // 1ms
    // the timing on this doesn't really make sense to me
  }
}
