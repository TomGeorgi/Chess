package de.htwg.se.Chess.model

package de.htwg.se.Chess.model

case class Bishop(c: Color.Value) extends Figure {
  override val color: Color.Value = c
  override val typ: FigureType.Value = FigureType.BISHOP


  override def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: Grid): Boolean = {
    true
  }

  override def getType(): FigureType.Value = typ

  override def toString: String = {
    color match {
      case Color.BLACK => "♝"
      case Color.WHITE => "♗"
    }
  }
}