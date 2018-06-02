package de.htwg.se.Chess.model

trait Figure {

  val color: Color.Value
  val typ: FigureType.Value

  def getType(): FigureType.Value

  def move(oldCol: Int, oldRow: Int, newCol: Int, newRow: Int, grid: Grid): Boolean
}

object FigureType extends Enumeration {
  val EMPTY, PAWN, ROOK, KING, QUEEN, KNIGHT, BISHOP = Value
}

object Color extends Enumeration {
  val EMPTY, BLACK, WHITE = Value
}
