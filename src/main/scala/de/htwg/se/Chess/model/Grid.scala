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

  override def toString: String = {
    var i = 1
    val lineseparator = "|" + "---+" * (size-1) + "---|\n"
    val line = "| x " * size + "|\n"
    print("  A   B   C   D   E   F   G   H")
    var box = "\n" + (lineseparator + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }
}