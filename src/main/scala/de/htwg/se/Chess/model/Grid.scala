package de.htwg.se.Chess.model

case class Grid(private val cells:Matrix[Cell]) {

  def this(size:Int) = this(new Matrix[Cell](size, Cell(None)))

  val size:Int = cells.size

  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value: Option[Figure]):Grid = copy(cells.replaceCell(row, col, Cell(value)))

  def fill(): Grid = {
    var fillGrid: Grid = set(0, 0, Some(Rook(Color.BLACK)))
    fillGrid = fillGrid.set(0, 1, Some(Knight(Color.BLACK)))
    fillGrid = fillGrid.set(0, 2, Some(Bishop(Color.BLACK)))
    fillGrid = fillGrid.set(0, 3, Some(Queen(Color.BLACK)))
    fillGrid = fillGrid.set(0, 4, Some(King(Color.BLACK)))
    fillGrid = fillGrid.set(0, 5, Some(Bishop(Color.BLACK)))
    fillGrid = fillGrid.set(0, 6, Some(Knight(Color.BLACK)))
    fillGrid = fillGrid.set(0, 7, Some(Rook(Color.BLACK)))
    fillGrid = fillGrid.set(7, 0, Some(Rook(Color.WHITE)))
    fillGrid = fillGrid.set(7, 1, Some(Knight(Color.WHITE)))
    fillGrid = fillGrid.set(7, 2, Some(Bishop(Color.WHITE)))
    fillGrid = fillGrid.set(7, 3, Some(Queen(Color.WHITE)))
    fillGrid = fillGrid.set(7, 4, Some(King(Color.WHITE)))
    fillGrid = fillGrid.set(7, 5, Some(Bishop(Color.WHITE)))
    fillGrid = fillGrid.set(7, 6, Some(Knight(Color.WHITE)))
    fillGrid = fillGrid.set(7, 7, Some(Rook(Color.WHITE)))

    for {
      col <- 0 until size
    } fillGrid = fillGrid.set(1, col, Some(Pawn(Color.BLACK)))

    for {
      col <- 0 until size
    } fillGrid = fillGrid.set(6, col, Some(Pawn(Color.WHITE)))

    fillGrid
  }

  var isInCheckColor: Color.Value = Color.EMPTY

  def isInCheck(colorToCheck: Color.Value): Boolean = {
    var kingPos: (Int, Int) = (-1, -1)
    for (row <- 0 to 7) {
      for (col <- 0 to 7) {
        if(cell(row, col).value == Some(King(colorToCheck))) {
          kingPos = (row, col)
        }
      }
    }
    val revColor: Color.Value = Color.colorReverse(colorToCheck)
    if(getAllOtherColorAndCheck(kingPos, revColor, this)){
      isInCheckColor = colorToCheck
      true
    } else {
      false
    }
  }

  def getAllOtherColorAndCheck(kingPos: (Int, Int), revColor: Color.Value, gridC: Grid): Boolean = {
    var figureList: List[(Figure, (Int, Int))] = Nil
    for (row <- 0 to 7) {
      for (col <- 0 to 7) {
        cell(row, col).value match {
          case Some(s) => {
            if (s.color == revColor) {
              figureList = (s, (row, col)) :: figureList
            }
          }
          case None =>
        }
      }
    }
    for {
      fig <- figureList
    } if (fig._1.move(fig._2._1, fig._2._2, kingPos._1, kingPos._2, gridC)) return true
    false
  }

  override def toString: String = {
    val numbers = "y "
    val lineseparator = "  |" + "---+" * (size-1) + "---|\n"
    val line = "| x " * size + "|\n"
    print("\n\n    A   B   C   D   E   F   G   H")
    var box = "\n" + (lineseparator + numbers + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    for {
      row <- 0 until size
    } box = box.replaceFirst("y", (size - row).toString)
    box
  }
}