package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model.Knight

class KnightSpec  extends WordSpec with Matchers{

  "A Queen" when {
    "set to correct move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Knight(Color.BLACK)))
      "have return true" in {
        move(0, 0, 1, 2, grid) should be(true)
      }
    }

    "set to invalid move" should {
      val grid = new Grid(8)
      var grid = set(0, 0, Some(Knight(Color.BLACK)))
      var grid = set(2, 1, Some(Knight(Color.BLACK)))
      "have return false" in {
        move(0, 0, 2, 1, grid) should be(false)
      }

      "set to correct attack" should {
        val grid = new Grid(8)
        var grid = set(4, 4, Some(Knight(Color.BLACK)))
        var grid = set(6, 3, Some(Knight(Color.WHITE)))
        "have return true" in {
          move(4, 4, 6, 3, grid) should be(true)
        }
      }

      "set to incorrect move" should {
        val grid = new Grid(8)
        var grid = set(3, 4, Some(Knight(Color.BLACK)))
        "have return false" in {
          move(3, 4, 4, 4, grid) should be(false)
        }
      }

      "Set to jump over figure" should {
        val grid = new Grid(8)
        var grid = set(3, 4, Some(Knight(Color.BLACK)))
        val grid = set(5, 4, Some(Knight(Color.WHITE)))
        "have return false" in {
          move(3, 4, 5, 5, grid) should be(true)
        }
      }
      "Set to stay" should {
        val grid = new Grid(8)
        var grid = set(5, 5, Some(Knight(Color.BLACK)))
        "have return false" in {
          move(5, 5, 5, 5, grid) should be(false)
        }
      }
    }
  }
}
