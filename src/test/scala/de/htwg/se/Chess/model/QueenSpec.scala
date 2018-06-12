package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model.Queen

@RunWith(classOf[JUnitRunner])
class QueenSpec extends WordSpec with Matchers {

  "A Queen" when {
    "set to correct move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Queen(Color.BLACK)))
      "have return true" in {
        move(0, 0, 1, 1, grid) should be(true)
      }
    }

    "set to correct move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Queen(Color.BLACK)))
      "have return true" in {
        move(0, 0, 1, 0, grid) should be(true)
      }
    }

    "set to invalid move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Queen(Color.BLACK)))
      var grid = set(1, 1, Some(Queen(Color.BLACK)))
      "have return false" in {
        move(0, 0, 2, 2, grid) should be(false)
      }

      "set to correct attack" should {
        val grid = new Grid(8)
        var grid = set(0, 0, Some(Queen(Color.BLACK)))
        var grid = set(2, 2, Some(Queen(Color.WHITE)))
        "have return true" in {
          move(0, 0, 2, 2, grid) should be(true)
        }
      }

      "set to incorrect move" should {
        val grid = new Grid(8)
        var grid = set(4, 4, Some(Queen(Color.BLACK)))
        "have return false" in {
          move(4, 4, 2, 4, grid) should be(false)
        }
      }
      "Set to stay" should {
        val grid = new Grid(8)
        var grid = set(3, 3, Some(Queen(Color.BLACK)))
        "have return false" in {
          move(3, 3, 3, 3, grid) should be(false)
        }
      }
    }
  }
}

