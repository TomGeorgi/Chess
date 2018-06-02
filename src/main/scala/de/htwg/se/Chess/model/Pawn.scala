package de.htwg.se.Chess.model

case class Pawn(c: Color.Value) extends Figure {

  override val color: Color.Value = c

  override val typ: FigureType.Value = FigureType.PAWN

  override def getType(): FigureType.Value = {
    typ
  }

  override def move(oldCol: Int, oldRow: Int, newCol: Int, newRow: Int, grid: Grid): Boolean = true

  override def toString: String = {
    color match {
      case Color.BLACK => "♟"
      case Color.WHITE => "♙"
    }
  }
}