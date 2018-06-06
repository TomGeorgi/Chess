package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model.{Color, Grid, Pawn, Rook}
import de.htwg.se.Chess.util.Observer
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {


  "A Controller" when {
    "observed by an Observer" should {
      val newGrid = new Grid(8)
      val controller = new Controller(newGrid, "Player 1", "Player 2")
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update: Unit = updated = true
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyGrid("Player 1", "Player 2")
        observer.updated should be(true)
        controller.grid.size should be(8)
      }
      "notify its Observer after setting a cell" in {
        controller.set(0, 6, "Rook", "w")
        observer.updated should be(true)
        controller.grid.cell(0, 6).value should be(Some(Rook(Color.WHITE)))
      }
    }
  }
  "empty" should {
    val grid = new Grid(8)
    val controller = new Controller(grid, "Player 1", "Player 2")
    "handle undo/redo after a pull" in {
      controller.grid.cell(0, 4).isSet should be(false)
      controller.set(0, 4, "Rook", "w")
      controller.grid.cell(0, 4).isSet should be(true)
      controller.undo
      controller.grid.cell(0, 4).isSet should be(false)
      controller.redo
      controller.grid.cell(0, 4).isSet should be(true)
    }
  }
}
