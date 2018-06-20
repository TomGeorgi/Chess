package de.htwg.se.Chess.model.figureComponent

import de.htwg.se.Chess.model.gridComponent.GridInterface

trait Figure {

  val color: Color.Value
  val typ: FigureType.Value

  def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: GridInterface): Boolean

  def colorReverse(color: Color.Value): Color.Value
}

object FigureType extends Enumeration {
  val PAWN, ROOK, KING, QUEEN, KNIGHT, BISHOP = Value
}

object Color extends Enumeration {
  val EMPTY, BLACK, WHITE = Value

  def colorReverse(color: Color.Value): Color.Value = color match {
      case Color.WHITE => Color.BLACK
      case Color.BLACK => Color.WHITE
      case Color.EMPTY => Color.EMPTY
    }

}
