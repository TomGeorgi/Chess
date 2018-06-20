package de.htwg.se.Chess.model

import de.htwg.se.Chess.model.figureComponent.Color
import de.htwg.se.Chess.model.figureComponent.figureBaseImpl.Queen
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class QueenSpec extends WordSpec with Matchers {
  var grid: GridInterface = new Grid(8)

  "A Queen" when {
    "set to correct move" should {
      grid = grid.set(2, 2, Some(Queen(Color.WHITE)))
      "have return true" in {
        Queen(Color.WHITE).move(2, 2, 4, 4, grid) should be(true)
      }
    }

    "set a correct move to an empty cell" should {
      grid = grid.set(7, 7, Some(Queen(Color.BLACK)))
      "have return true" in {
        Queen(Color.BLACK).move(7, 0, 3, 4, grid) should be(true)
      }
    }

    "set a invalid move" should {
      grid = grid.set(6, 6, Some(Queen(Color.BLACK)))
      grid = grid.set(4, 4, Some(Queen(Color.BLACK)))
      "have return false" in {
        Queen(Color.BLACK).move(6, 6, 4, 4, grid) should be(false)
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

