package futurespromises

import scala.util.{Failure, Success, Random, Try}
import org.junit.{Assert, Test}
import scala.concurrent.Future

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
    if(beans == "baked beans") throw GrindingException("are you jokin?")
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

  @Test def foo(){
    grind("baked beans")
  }

  @Test def test(){
    val response = grind("baked beans")
    println("response: " + response)
  }




}
