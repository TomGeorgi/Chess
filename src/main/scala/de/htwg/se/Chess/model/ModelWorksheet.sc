
import de.htwg.se.Chess.model.Grid

val grid1 = new Grid(4)

grid1.cell(0, 0).isSet
grid1.set(1, 0, "4")

grid1.cell(0,0).isSet
val grid2 = grid1.set(0,0,"1")
grid2.cell(0,0).isSet

