package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model.Bishop

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  val player: (String, String) = ("Player 1", "Player 2")

  "A Bishop" when {
    "set to correct move" should {
      val grid = new Grid(8)
      var grid = set(0, 1, Some(Bishop(Color.BLACK)))
      "have return true" in {
        move(0, 1, 1, 0, grid) should be(true)
      }
    }

    "set to invalid move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Bishop(Color.BLACK)))
      var grid = set(2, 2, Some(Bishop(Color.BLACK)))
      "have return false" in {
        move(0, 0, 3, 3, grid) should be(false)
      }

      "set to correct attack" should {
        val grid = new Grid(8)
        var grid = set(6, 6, Some(Bishop(Color.BLACK)))
        var grid = set(4, 4, Some(Bishop(Color.WHITE)))
        "have return true" in {
          move(6, 6, 4, 4, grid) should be(true)
        }
      }

      "set to incorrect move" should {
        val grid = new Grid(8)
        var grid = set(3, 3, Some(Bishop(Color.BLACK)))
        "have return false" in {
          move(3, 3, 1, 5, grid) should be(false)
        }
      }
      "Set to stay" should {
        val grid = new Grid(8)
        var grid = set(5, 5, Some(Bishop(Color.BLACK)))
        "have return false" in {
          move(5, 5, 5, 5, grid) should be(false)
        }
      }
    }
  }
}

