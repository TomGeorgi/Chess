
package de.htwg.se.Chess.model.figureComponent

import de.htwg.se.Chess.model.figureComponent.figureBaseImpl.Knight
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KnightSpec  extends WordSpec with Matchers{
  var grid: GridInterface = new Grid(8)

  "A Knight" when {
    "set to correct move" should {
      grid = grid.set(0, 0, Some(Knight(Color.BLACK)))
      "have return true" in {
        Knight(Color.BLACK).move(0, 0, 1, 2, grid) should be(true)
      }
    }

    "set a correct move to an empty cell" should {
      grid = grid.set(0, 1, Some(Knight(Color.WHITE)))
      "have return true" in {
        Knight(Color.WHITE).move(0, 1, 1, 3, grid) should be(true)
      }
    }

    "set to invalid move" should {
      grid = grid.set(2, 1, Some(Knight(Color.BLACK)))
      "have return false" in {
        Knight(Color.BLACK).move(0, 0, 2, 1, grid) should be(false)
      }
    }

    "set to correct attack" should {
      grid = grid.set(4, 4, Some(Knight(Color.BLACK)))
      grid = grid.set(6, 3, Some(Knight(Color.WHITE)))
      "have return true" in {
        Knight(Color.BLACK).move(4, 4, 6, 3, grid) should be(true)
      }
    }

    "set to incorrect move" should {
      grid = grid.set(3, 4, Some(Knight(Color.BLACK)))
      "have return false" in {
        Knight(Color.BLACK).move(3, 4, 4, 4, grid) should be(false)
      }
    }

    "Set to jump over figure" should {
      grid = grid.set(5, 4, Some(Knight(Color.WHITE)))
      "have return false" in {
        Knight(Color.BLACK).move(3, 4, 5, 5, grid) should be(true)
      }
    }

    "Set to stay" should {
      "have return false" in {
        Knight(Color.BLACK).move(3, 4, 3, 4, grid) should be(false)
      }
    }

    "Set to Empty Cell" should {
      "have return true" in {
        Knight(Color.BLACK).move(3, 4, 1, 5, grid) should be(true)
      }
    }
    "Get All Moves" should {
      var grid: GridInterface = new Grid(8)
      grid = grid.set(3, 3, Some(Knight(Color.WHITE)))
      grid = grid.set(1, 2, Some(Knight(Color.WHITE)))
      grid = grid.set(1, 4, Some(Knight(Color.WHITE)))
      grid = grid.set(2, 5, Some(Knight(Color.WHITE)))
      grid = grid.set(4, 5, Some(Knight(Color.BLACK)))
      grid = grid.set(5, 2, Some(Knight(Color.WHITE)))
      grid = grid.set(4, 1, Some(Knight(Color.WHITE)))
      grid = grid.set(2, 1, Some(Knight(Color.WHITE)))
      "have return" in {
        Knight(Color.WHITE).moveAll(3, 3, grid) should be(List((4,5), (5,4)))
      }
    }
  }
}


