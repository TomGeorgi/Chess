package de.htwg.se.Chess.model.figureComponent

import de.htwg.se.Chess.model.figureComponent.figureBaseImpl.King
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KingSpec extends WordSpec with Matchers {
  var grid: GridInterface = new Grid(8)

  "A King" when {
    "set to correct move" should {
      grid = grid.set(0, 1, Some(King(Color.BLACK)))
      "have return true" in {
        King(Color.BLACK).move(0, 1, 1, 0, grid) should be(true)
      }
    }

    "set a invalid move to an cell with the same Figure color" should {
      grid = grid.set(3, 3, Some(King(Color.WHITE)))
      grid = grid.set(4, 4, Some(King(Color.WHITE)))
      "have return false" in {
        King(Color.WHITE).move(3, 3, 4, 4, grid) should be(false)
      }
    }

    "set a correct move to an empty cell" should {
      grid = grid.set(7, 0, Some(King(Color.BLACK)))
      "have return true" in {
        King(Color.BLACK).move(7, 0, 6, 0, grid) should be(true)
      }
    }

    "set a invalid move" should {
      grid = grid.set(6, 6, Some(King(Color.BLACK)))
      grid = grid.set(5, 5, Some(King(Color.BLACK)))
      "have return false" in {
        King(Color.BLACK).move(6, 6, 5, 5, grid) should be(false)
      }
    }

    "set to invalid move" should {
      grid = grid.set(0, 0, Some(King(Color.BLACK)))
      grid = grid.set(0, 1, Some(King(Color.BLACK)))
      "have return false" in {
        King(Color.BLACK).move(0, 0, 0, 1, grid) should be(false)
      }
    }

    "set to correct attack" should {
      grid = grid.set(6, 6, Some(King(Color.BLACK)))
      grid = grid.set(5, 7, Some(King(Color.WHITE)))
      "have return true" in {
        King(Color.BLACK).move(6, 6, 5, 7, grid) should be(true)
      }
    }

    "set to incorrect move" should {
      grid = grid.set(3, 3, Some(King(Color.BLACK)))
      "have return false" in {
        King(Color.BLACK).move(3, 3, 3, 5, grid) should be(false)
      }
    }
    "Set to stay" should {
      grid = grid.set(5, 5, Some(King(Color.BLACK)))
      "have return false" in {
        King(Color.BLACK).move(5, 5, 5, 5, grid) should be(false)
      }
    }
  }
}
