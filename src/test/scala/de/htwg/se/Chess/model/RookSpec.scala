package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.Chess.controller.Controller

@RunWith(classOf[JUnitRunner])
class RookSpec extends WordSpec with Matchers {
  val player: (String, String) = ("Player 1", "Player 2")

  "A Rook" when {
    "set to correct move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Rook(Color.BLACK)))
      "have return true" in {
        move(0, 0, 1, 0, grid) should be(true)
      }
    }

    "set to invalid move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Rook(Color.BLACK)))
      var grid = set(0, 1, Some(Rook(Color.BLACK)))
      "have return false" in {
        move(0, 0, 0, 3, grid) should be(false)
      }

      "set to correct attack" should {
        val grid = new Grid(8)
        var grid = set(0, 0, Some(Rook(Color.BLACK)))
        var grid = set(2, 0, Some(Rook(Color.WHITE)))
        "have return true" in {
          move(0, 0, 2, 0, grid) should be(true)
        }
      }

      "set to incorrect move" should {
        val grid = new Grid(8)
        var grid = set(0, 0, Some(Rook(Color.BLACK)))
        "have return false" in {
          move(0, 0, 1, 1, grid) should be(false)
        }
      }
    }


  }
}
