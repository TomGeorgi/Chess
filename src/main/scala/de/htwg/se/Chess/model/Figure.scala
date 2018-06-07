package de.htwg.se.Chess.model

trait Figure {

  val color: Color.Value
  val typ: FigureType.Value

  def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: Grid): Boolean

  def colorReverse(color: Color.Value): Color.Value
}

object FigureType extends Enumeration {
  val EMPTY, PAWN, ROOK, KING, QUEEN, KNIGHT, BISHOP = Value
}

object Color extends Enumeration {
  val EMPTY, BLACK, WHITE = Value
}
