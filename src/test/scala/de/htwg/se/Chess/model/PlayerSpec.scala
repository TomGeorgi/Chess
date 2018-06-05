package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", Color.BLACK)
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a color" in {
        player.color should be(Color.BLACK)
      }
      "have a nice String representation" in {
        player.toString should be("Your Name")
      }
    }
  }
}
