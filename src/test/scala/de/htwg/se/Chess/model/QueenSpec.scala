package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class QueenSpec extends WordSpec with Matchers {
  var grid: Grid = new Grid(8)

  "A Queen" when {
    "set to correct move" should {
      grid = grid.set(2, 2, Some(Queen(Color.WHITE)))
      "have return true" in {
        Queen(Color.WHITE).move(2, 2, 4, 4, grid) should be(true)
      }
    }

    "set to correct move variant 2" should {
      grid = grid.set(0 , 0, Some(Queen(Color.WHITE)))
      "have return true" in {
        Queen(Color.WHITE).move(0, 0, 1, 0, grid) should be(true)
      }
    }

    "set to invalid move" should {
      grid = grid.set(1, 1, Some(Queen(Color.WHITE)))
      "have return false" in {
        Queen(Color.WHITE).move(0, 0, 2, 2, grid) should be(false)
      }
    }

    "set to correct attack" should {
      grid = grid.set(1, 1, Some(Queen(Color.BLACK)))
      "have return true" in {
        Queen(Color.WHITE).move(2, 2, 1, 1, grid) should be(true)
      }
    }

    "set to incorrect move" should {
      "have return false" in {
        Queen(Color.WHITE).move(0, 0, 2, 4, grid) should be(false)
      }
    }
    "Set to stay" should {
      "have return false" in {
        Queen(Color.WHITE).move(0, 0, 0, 0, grid) should be(false)
      }
    }
  }
}

