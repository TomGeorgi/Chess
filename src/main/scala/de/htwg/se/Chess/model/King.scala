package de.htwg.se.Chess.model

case class King(c: Color.Value) extends Figure {
  override val color: Color.Value = c
  override val typ: FigureType.Value = FigureType.KING

  override def move(oldCol: Int, oldRow: Int, newCol: Int, newRow: Int, grid: Grid): Boolean = {
    true
  }

  override def getType(): FigureType.Value = typ
}
