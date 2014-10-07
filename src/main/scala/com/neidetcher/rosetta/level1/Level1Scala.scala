package com.neidetcher.rosetta.level1

class Level1Scala extends Level1{

  def helloWorld(name: String): String = {
    "hello " + name
  }

  def loopName(name: String, times: Integer): String = {
    name * times;
  }


}
