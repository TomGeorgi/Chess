package de.htwg.se.Chess.model

<<<<<<< HEAD

case class Pawn_white() {

}

case class Pawn_black() {

=======
case class Pawn(c: Color.Value) extends Figure {

  override val color: Color.Value = c

  override val typ: FigureType.Value = FigureType.PAWN

  override def getType(): FigureType.Value = {
    typ
  }

  override def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: Grid): Boolean = {
    true
  }

  override def toString: String = {
    color match {
      case Color.BLACK => "♟"
      case Color.WHITE => "♙"
    }
  }
>>>>>>> Dev
}
