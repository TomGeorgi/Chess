package de.htwg.se.Chess.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Chess.controller.controllerComponent.GameStatus._
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.figureComponent.{Color, Figure}
import de.htwg.se.Chess.model.figureComponent.figureBaseImpl._
import de.htwg.se.Chess.util.Command

class SetCommand(row: Int, col: Int, value: String, color: String, controller: Controller) extends Command {

  var memento: GridInterface = controller.grid

  override def doStep: Unit = {
    memento = controller.grid
    controller.grid = controller.grid.set(row, col, valueToFigure(value, color))
  }

  override def undoStep: Unit = {
    val new_memento = controller.grid
    controller.grid = memento
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.grid
    controller.grid = memento
    memento = new_memento
  }


  def valueToFigure(value: String, color: String): Option[Figure] = {
    val c = color match {
      case "w" => Color.WHITE
      case "b" => Color.BLACK
      case "_" => Color.EMPTY
    }

    value match {
      case "Bauer" | "Pawn" => Some(Pawn(c))
      case "Turm" | "Rook" => Some(Rook(c))
      case "Springer" | "Knight" => Some(Knight(c))
      case "Läufer" | "Bishop" => Some(Bishop(c))
      case "König" | "King" => Some(King(c))
      case "Königin" | "Queen" => Some(Queen(c))
      case "_" => None
    }
  }
}
