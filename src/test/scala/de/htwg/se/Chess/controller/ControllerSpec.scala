package de.htwg.se.Chess.controller

import de.htwg.se.Chess.controller.controllerComponent.GameStatus
import de.htwg.se.Chess.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Chess.model.figureComponent.Color
import de.htwg.se.Chess.model.figureComponent.figureBaseImpl._
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Chess.model.playerComponent.playerBaseImpl.Player
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {


  "A Controller" when {
    "with a empty Grid" should {
      val grid = new Grid(8)
      val controller = new Controller(grid, "Player 1", "Player 2")
      controller.createNewGrid("Player 3", "Player 4")
      val player1 = Player("Player 3", Color.WHITE)
      val player2 = Player("Player 4", Color.BLACK)
      "has two players and one at turn" in {
        controller.playerAtTurn should be(player1)
        controller.setNextPlayer
        controller.playerAtTurn should be(player2)
        controller.playerNotAtTurn should be(player1)
        controller.playerAtTurnToString should be("Player 4")
      }
      "handle undo/redo if a new game was started" in {
        controller.grid.fill()
        controller.grid.cell(0, 0).isSet should be(true)
        controller.undo
        controller.grid.cell(0, 0).isSet should be(true)
        controller.redo
        controller.grid.cell(0, 0).isSet should be(true)

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
      val controller = new Controller(grid.fill(), "Player 1", "Player 2")
      controller.turn(4, 0, 7, 2)
      "the gamestatus is set to MOVE_NOT_VALID" in {
        controller.gameStatus should be(GameStatus.MOVE_NOT_VALID)
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
  "empty grid" should {
    val grid = new Grid(8)
    val controller = new Controller(grid, "Player 1", "Player 2")
    val player1 = Player("Player 1", Color.WHITE)
    val player2 = Player("Player 2", Color.BLACK)
    "handle set of a turn" in {
      controller.set(0, 0, "Rook", "w")
      controller.grid.cell(0, 0).isSet should be(true)
      controller.grid.cell(1, 0).isSet should be(false)
      controller.turn(0, 0, 1, 0)
      controller.playerAtTurn should be(player2)
      controller.grid.cell(1, 0).isSet should be(true)
      controller.set(0, 7, "Knight", "b")
      controller.grid.cell(0, 7).isSet should be(true)
      controller.grid.cell(1, 5).isSet should be(false)
      controller.turn(0, 7, 1, 5)
      controller.grid.cell(0, 7).isSet should be(false)
      controller.grid.cell(1, 5).isSet should be(true)
      controller.set(1, 1, "Bishop", "w")
      controller.grid.cell(1, 1).isSet should be(true)
      controller.grid.cell(2, 2).isSet should be(false)
      controller.turn(1, 1, 2, 2)
      controller.grid.cell(1, 1).isSet should be(false)
      controller.grid.cell(2, 2).isSet should be(true)
      controller.set(7, 4, "King", "b")
      controller.grid.cell(7, 4).isSet should be(true)
      controller.grid.cell(6, 3).isSet should be(false)
      controller.turn(7, 4, 6, 3)
      controller.grid.cell(7, 4).isSet should be(false)
      controller.grid.cell(6, 3).isSet should be(true)
      controller.set(3, 0, "Queen", "w")
      controller.grid.cell(3, 0).isSet should be(true)
      controller.grid.cell(3, 3).isSet should be(false)
      controller.turn(3, 0, 3, 3)
      controller.grid.cell(3, 0).isSet should be(false)
      controller.grid.cell(3, 3).isSet should be(true)
    }
    "handle a set of turn by an empty cell" in {
      controller.grid.cell(6, 6).isSet should be(false)
      controller.grid.cell(0, 0).isSet should be(false)
      controller.turn(6, 6, 0, 0)
      controller.grid.cell(6, 6).isSet should be(false)
      controller.grid.cell(0, 0).isSet should be(false)
    }
  }
  "handle is check" should {
    var grid: GridInterface = new Grid(8)
    val controller = new Controller(grid, "Player 1", "Player 2")
    controller.grid = controller.grid.set(0, 2, Some(Bishop(Color.BLACK)))
    controller.grid = controller.grid.set(3, 1, Some(Queen(Color.WHITE)))
    controller.grid = controller.grid.set(3, 3, Some(King(Color.WHITE)))
    controller.turn(3, 1, 3, 2)
    controller.turn(0, 2, 1, 1)
    "when the king is under attack of an enemy figure" in {
      controller.grid.isInCheck(Color.WHITE) should be(true)
      controller.gameStatus should be(GameStatus.CHECK)
    }
  }
  "is checkmate" should {
    var grid: GridInterface = new Grid(8)
    val controller = new Controller(grid, "Player 1", "Player 2")
    controller.grid = controller.grid.set(0 ,0, Some(King(Color.BLACK)))
    controller.grid = controller.grid.set(2, 0, Some(Rook(Color.WHITE)))
    controller.grid = controller.grid.set(0, 2, Some(Rook(Color.WHITE)))
    controller.grid = controller.grid.set(3, 2, Some(Queen(Color.WHITE)))
    controller.grid = controller.grid.set(7, 7, Some(King(Color.WHITE)))
    controller.turn(3, 2, 2, 2)
    "when the is under attack and he cant be saved with the next move" in {
      controller.grid.isCheckMate(Color.BLACK)
      controller.gameStatus should be(GameStatus.CHECK_MATE)
    }
  }
  "handle playing after checkmate" should {
    var grid: GridInterface = new Grid(2)
    val controller = new Controller(grid, "Player 1", "Player 2")
    controller.grid = controller.grid.set(0, 0, Some(Pawn(Color.BLACK)))
    controller.gameStatus = GameStatus.CHECK_MATE
    "figure stands on the same position as before" in {
      controller.grid.cell(0, 0).isSet should be(true)
      controller.grid.cell(1, 0).isSet should be(false)
      controller.gameStatus should be(GameStatus.CHECK_MATE)
      controller.turn(0, 0, 1, 0)
      controller.grid.cell(0, 0).isSet should be(true)
      controller.grid.cell(1, 0).isSet should be(false)
      controller.gameStatus should be(GameStatus.CHECK_MATE)
    }
  }
  "when a game is saved and loaded" should {
    val smallGrid = new Grid(8)
    val controller = new Controller(smallGrid, "Player 1", "Player 2")
    controller.save
    val controllerTest = new Controller(new Grid(8), "p1", "p2")
    controllerTest.load
    "be the same" in {
      controller.grid should be(controllerTest.grid)
    }
  }
}