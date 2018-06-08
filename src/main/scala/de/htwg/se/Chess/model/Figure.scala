package de.htwg.se.Chess.model

trait Figure {

  val color: Color.Value
  val typ: FigureType.Value

  def getType(): FigureType.Value

  def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: Grid): Boolean
}

object FigureType extends Enumeration {
  val EMPTY, PAWN, ROOK, KING, QUEEN, KNIGHT, BISHOP = Value
}

object Color extends Enumeration {
  val EMPTY, BLACK, WHITE = Value
}
