
package de.htwg.se.Chess.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  var grid: Grid = new Grid(8)

  "A Bishop" when {
    "set to correct move" should {
      grid = grid.set(0, 1, Some(Bishop(Color.BLACK)))
      "have return true" in {
        Bishop(Color.BLACK).move(0, 1, 1, 0, grid) should be(true)
      }
    }

    "set to invalid move" should {
      grid = grid.set(0, 0, Some(Bishop(Color.BLACK)))
      grid = grid.set(2, 2, Some(Bishop(Color.BLACK)))
      "have return false" in {
        Bishop(Color.BLACK).move(0, 0, 3, 3, grid) should be(false)
      }
    }

    "set to correct attack" should {
      grid = grid.set(6, 6, Some(Bishop(Color.BLACK)))
      grid = grid.set(5, 7, Some(Bishop(Color.WHITE)))
      "have return true" in {
        Bishop(Color.BLACK).move(6, 6, 5, 7, grid) should be(true)
      }
    }

    "set to incorrect move" should {
      grid = grid.set(3, 3, Some(Bishop(Color.BLACK)))
      "have return false" in {
        Bishop(Color.BLACK).move(3, 3, 3, 5, grid) should be(false)
      }
    }
    "Set to stay" should {
      grid = grid.set(5, 5, Some(Bishop(Color.BLACK)))
      "have return false" in {
        Bishop(Color.BLACK).move(5, 5, 5, 5, grid) should be(false)
      }
    }
  }
}

