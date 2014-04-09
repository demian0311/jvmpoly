package futurespromises

import scala.util.Try
import org.junit.{Assert, Test}

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

  def grind(beans: CoffeeBeans): GroundCoffee = s"ground coffee of $beans"
  def heatWater(water: Water): Water = water.copy(temperature = 85)
  def frothMilk(milk: Milk): FrothedMilk = s"frothed $milk"
  def brew(coffee: GroundCoffee, heatedWater: Water): Espresso = "espresso"
  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  case class GrindingException(msg: String) extends Exception(msg)
  case class FrothingException(msg: String) extends Exception(msg)
  case class WaterBoilingException(msg: String) extends Exception(msg)
  case class BrewingException(msg: String) extends Exception(msg)

  def prepareCappuccino(): Try[Cappuccino] = for {
    ground <- Try(grind("arabica beans"))
    water <- Try(heatWater(Water(25)))
    espresso <- Try(brew(ground, water))
    foam <- Try(frothMilk("milk"))
  } yield combine(espresso, foam)

  @Test def test(){
    val result = prepareCappuccino()
    println("result: " + result)

    Assert.assertTrue(result.isSuccess)
    //println("result.get(): " + result.get())

    //Assert.assertEquals("cappuccino": Cappuccino, result.get())

    //assertEquals("cappuccino", result.get)
  }
}
