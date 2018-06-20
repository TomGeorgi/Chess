package de.htwg.se.Chess.model.figureComponent.figureBaseImpl

import de.htwg.se.Chess.model.figureComponent.{Color, Figure, FigureType}
import de.htwg.se.Chess.model.gridComponent.GridInterface

case class Pawn(c: Color.Value) extends Figure {

  override val color: Color.Value = c
  override val typ: FigureType.Value = FigureType.PAWN

  override def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: GridInterface): Boolean = {

    val revColor: Color.Value = colorReverse(color)
    val blackBeat = (1, 1) :: (1, -1) :: Nil
    val whiteBeat = (-1, 1) :: (-1, -1) :: Nil

    color match {
      case Color.WHITE => {
        if ((oldRow, oldCol) == (6, oldCol)) {
          if ((oldRow - 2, oldCol) == (newRow, newCol)) {
            if (grid.cell(newRow + 1, newCol).isSet || grid.cell(newRow, newCol).isSet) return false
            return true
          }
        }
        if ((oldRow - 1, oldCol) == (newRow, newCol)) {
          if (grid.cell(newRow, newCol).isSet) return false
          return true
        }
        for (i <- whiteBeat) {
          if ((oldRow + i._1, oldCol + i._2) == (newRow, newCol)) {
            if (grid.cell(newRow, newCol).isSet) {
              grid.cell(newRow, newCol).value match {
                case Some(res) => res.color match {
                  case `color` => return false
                  case `revColor` => return true
                }
                case None => return false
              }
            }
          }
        }
      }
      case Color.BLACK => {
        if ((oldRow, oldCol) == (1, oldCol)) {
          if ((oldRow + 2, oldCol) == (newRow, newCol)) {
            if (grid.cell(newRow - 1, newCol).isSet || grid.cell(newRow, newCol).isSet) return false
            return true
          }
        }
        if ((oldRow + 1, oldCol) == (newRow, newCol)) {
          if (grid.cell(newRow, newCol).isSet) return false
          return true
        }
        for (i <- blackBeat) {
          if ((oldRow + i._1, oldCol + i._2) == (newRow, newCol)) {
            if (grid.cell(newRow, newCol).isSet) {
              grid.cell(newRow, newCol).value match {
                case Some(res) => res.color match {
                  case `color` => return false
                  case `revColor` => return true
                }
                case None => return false
              }
            }
          }
        }
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
      case Color.BLACK => "♟"
      case Color.WHITE => "♙"
    }
  }
}