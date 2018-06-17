package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.Observer
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {


  "A Controller" when {
    "with a empty Grid" should {
      val grid = new Grid(8)
      val controller = new Controller(grid, "Player 1", "Player 2")
      controller.createEmptyGrid("Player 3", "Player 4")
      val player1 = Player("Player 3", Color.WHITE)
      val player2 = Player("Player 4", Color.BLACK)
      "has two players and one at turn" in {
        controller.playerAtTurn should be(player1)
        controller.setNextPlayer
        controller.playerAtTurn should be(player2)
      }
      "handle undo/redo of a turn" in {
        controller.grid.fill()
        controller.grid.cell(2, 0).isSet should be(false)
        controller.turn(1, 0, 2, 0)
        controller.grid.cell(2, 0).isSet should be(true)
        controller.undo
        controller.grid.cell(2, 0).isSet should be(false)
        controller.redo
        controller.grid.cell(2, 0).isSet should be(true)
        controller.undo

      }
      "handle undo/redo of a set" in {
        controller.grid.fill()
        controller.grid.cell(2, 0).isSet should be(false)
        controller.grid.cell(1, 0).value should be(Some(Pawn(Color.BLACK)))
        controller.set(1, 0, "Bishop", "w")
        controller.set(2, 0, "Pawn", "b")
        controller.grid.cell(2, 0).isSet should be(true)
        controller.grid.cell(2, 0).value should be(Some(Pawn(Color.BLACK)))
        controller.grid.cell(1, 0).value should be(Some(Bishop(Color.WHITE)))
        controller.undo
        controller.grid.cell(2, 0).isSet should be(false)
        controller.grid.cell(2, 0).value should be(None)
        controller.undo
        controller.grid.cell(1, 0).value should be(Some(Pawn(Color.BLACK)))
        controller.redo
        controller.redo
        controller.grid.cell(2, 0).isSet should be(true)
        controller.grid.cell(2, 0).value should be(Some(Pawn(Color.BLACK)))
        controller.grid.cell(1, 0).value should be(Some(Bishop(Color.WHITE)))
        controller.set(5, 2, "Knight", "b")
        controller.grid.cell(5, 2).value should be(Some(Knight(Color.BLACK)))
        controller.undo
        controller.grid.cell(5, 2).isSet should be(false)
        controller.set(3, 6, "Queen", "b")
        controller.grid.cell(3, 6).value should be(Some(Queen(Color.BLACK)))
        controller.undo
        controller.grid.cell(3, 6).isSet should be(false)
      }
      "handle undo/redo of a set for an empty cell" in {
        controller.grid.fill()
        controller.grid.cell(6, 0).isSet should be(true)
        controller.set(6, 0, "_", "_")
        controller.grid.cell(6, 0).isSet should be(false)
        controller.undo
        controller.grid.cell(6, 0).isSet should be(true)
        controller.redo
        controller.grid.cell(6, 0).isSet should be(false)
      }
    }
    "when a player does a turn at the begin of a game" should {
      val grid = new Grid(8).fill()
      val controller = new Controller(grid, "Player 1", "Player 2")
      controller.turn(6, 0, 4, 0)
      "has 16 Cells filled with Figures and 48 Empty Cells" in {
        for (i <- 0 to 7) {
          controller.grid.cell(0, i).isSet should be(true)
          controller.grid.cell(1, i).isSet should be(true)
          controller.grid.cell(2, i).isSet should be(false)
          controller.grid.cell(3, i).isSet should be(false)
          controller.grid.cell(5, i).isSet should be(false)
          controller.grid.cell(7, i).isSet should be(true)
        }
        controller.grid.cell(4, 0).isSet should be(true)
        controller.grid.cell(4, 0).value should be(Some(Pawn(Color.WHITE)))
        controller.grid.cell(6, 0).isSet should be(false)
        for (i <- 1 to 7) {
          controller.grid.cell(4, i).isSet should be(false)
          controller.grid.cell(6, i).isSet should be(true)
        }
      }
      "the Game-Status is set to NEXT_PLAYER" in {
        controller.gameStatus should be(GameStatus.NEXT_PLAYER)
      }
      "The Player at turn is the other player after the first turn" in {
        controller.playerAtTurn should be(Player("Player 2", Color.BLACK))
      }
    }
    "when a player does an invalid move" should {
      val grid = new Grid(8)
      val controller = new Controller(grid, "Player 1", "Player 2")
      controller.turn(6, 0, 4, 0)
      "the gamestatus is set to MOVE_NOT_VALID" in {
        controller.gameStatus should be(GameStatus.MOVE_NOT_VALID)
      }
    }
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
      controller.set(0, 4, "King", "w")
      controller.grid.cell(0, 4).isSet should be(true)
      controller.undo
      controller.grid.cell(0, 4).isSet should be(false)
      controller.redo
      controller.grid.cell(0, 4).isSet should be(true)
    }
  }
}
