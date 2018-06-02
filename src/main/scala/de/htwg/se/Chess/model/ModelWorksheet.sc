
import de.htwg.se.Chess.model._

val grid1 = new Grid(4)

grid1.set(1, 3, FigureType.PAWN, Color.WHITE)

grid1.cell(1, 3).isSet
//grid1.turn(1, 0, "4")