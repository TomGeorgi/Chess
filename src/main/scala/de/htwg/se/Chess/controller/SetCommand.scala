package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.Command

class SetCommand(col: Int, row: Int, value: String, color: String, controller: Controller) extends Command {

  override def doStep: Unit = controller.grid = controller.grid.set(col, row, valueToFigure(value, color))

  override def undoStep: Unit = controller.grid = controller.grid.set(col, row, None)

  override def redoStep: Unit = controller.grid = controller.grid.set(col, row, valueToFigure(value, color))


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
    }
  }
}
