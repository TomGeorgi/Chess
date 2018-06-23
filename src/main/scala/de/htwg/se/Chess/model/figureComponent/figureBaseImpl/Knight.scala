package de.htwg.se.Chess.model.figureComponent.figureBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import de.htwg.se.Chess.model.figureComponent.{Color, Figure, FigureType}
import de.htwg.se.Chess.model.gridComponent.GridInterface

case class Knight @AssistedInject() (@Assisted c: Color.Value) extends Figure {

  override val color: Color.Value = c
  override val typ: FigureType.Value = FigureType.KNIGHT

  override def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: GridInterface): Boolean = {
    val getMove = (oldRow + 1, oldCol - 2) :: (oldRow + 2, oldCol - 1) :: (oldRow + 2, oldCol + 1) :: (oldRow + 1, oldCol + 2) :: (oldRow - 1, oldCol + 2) :: (oldRow - 2, oldCol + 1) :: (oldRow - 2, oldCol - 1) :: (oldRow - 1, oldCol - 2) :: Nil
    val revColor: Color.Value = colorReverse(color)

    for (valPos <- getMove) {
      if (valPos == (newRow, newCol)) {
        if (grid.cell(newRow, newCol).isSet) {
          grid.cell(newRow, newCol).value match {
            case Some(res) => res.color match {
              case `color` => return false
              case `revColor` => return true
            }
            case None => return true
          }
        } else return true
      }
    }
    false
  }

  override def colorReverse(color: Color.Value): Color.Value = color match {
    case Color.WHITE => Color.BLACK
    case Color.BLACK => Color.WHITE
  }

  override def toString: String = {
    color match {
      case Color.BLACK => "♞"
      case Color.WHITE => "♘"
    }
  }
}
