package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model._
import de.htwg.se.Chess.model.GameStatus._
import de.htwg.se.Chess.util.Command

class TurnCommand(oldCol: Int, oldRow: Int, newCol: Int, newRow: Int, controller: Controller) extends Command {

  var memento: (Grid, (Player, Player)) = (controller.grid, controller.player)

  override def doStep: Unit = {
    memento = (controller.grid, controller.player)

    val whichPlayer = controller.playerAtTurn
    val oldValue = controller.grid.cell(oldRow, oldCol).value
    var canSet: Boolean = false
    var color: Color.Value = Color.EMPTY

    oldValue match {
      case Some(res) => {
        color = res.color
        if (color == whichPlayer.color) {
          canSet = res.typ match {
            case FigureType.PAWN => {
              Pawn(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.ROOK => {
              Rook(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.KNIGHT => {
              Knight(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.BISHOP => {
              Bishop(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.QUEEN => {
              Queen(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.KING => {
              King(color).move(oldCol, oldRow, newCol, newRow, controller.grid)
            }
            case FigureType.EMPTY => {
              controller.gameStatus = NO_FIGURE
              false
            }
          }
        }
      }
      case None =>
    }

    if (canSet) {
      controller.grid = controller.grid.set(oldCol, oldRow, None)
      controller.grid = controller.grid.set(newCol, newRow, oldValue)
      controller.setNextPlayer
      controller.gameStatus = NEXT_PLAYER
    } else {
      controller.gameStatus = MOVE_NOT_VALID
    }
  }

  override def undoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }
}
