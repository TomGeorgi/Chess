package de.htwg.se.Chess.model

case class Grid(private val cells:Matrix[Cell]) {

  def this(size:Int) = this(new Matrix[Cell](size, Cell(None)))

  //noinspection ScalaStyle
  def turn(placeCol: Int, placeRow: Int, newPlaceCol: Int, newPlaceRow: Int, whichPlayer: Player, grid: Grid): (Grid, Boolean) = {
    val placeSet = cell(newPlaceCol, newPlaceRow).isSet
    val oldValue = cell(placeRow, placeCol).getValue
    var canSet: Boolean = false
    var color: Color.Value = Color.EMPTY
    var newGrid: Grid = grid

    oldValue match {
      case Some(res) => {
        color = res.color
        if (color == whichPlayer.color) {
          canSet = res.typ match {
            case FigureType.PAWN => Pawn(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.ROOK => Rook(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.KNIGHT => Knight(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.BISHOP => Bishop(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.QUEEN => Queen(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.KING => King(color).move(placeCol, placeRow, newPlaceCol, newPlaceRow, grid)
            case FigureType.EMPTY => false
          }
        }
      }
      case None => ()
    }

    if (canSet) {
      newGrid = set(placeCol, placeRow, None)
      newGrid = newGrid.set(newPlaceCol, newPlaceRow, oldValue)
      return (newGrid, true)
    } else return (this, false)
  }

  val size:Int = cells.size

  def cell(row:Int, col:Int):Cell = cells.cell(col, row)
  def set(col:Int, row:Int, value: Option[Figure]):Grid = copy(cells.replaceCell(col, row, Cell(value)))

  def fill(): Grid = {
    var fillGrid: Grid = set(0, 0, Some(Rook(Color.BLACK)))
    fillGrid = fillGrid.set(1, 0, Some(Knight(Color.BLACK)))
    fillGrid = fillGrid.set(2, 0, Some(Bishop(Color.BLACK)))
    fillGrid = fillGrid.set(3, 0, Some(Queen(Color.BLACK)))
    fillGrid = fillGrid.set(4, 0, Some(King(Color.BLACK)))
    fillGrid = fillGrid.set(5, 0, Some(Bishop(Color.BLACK)))
    fillGrid = fillGrid.set(6, 0, Some(Knight(Color.BLACK)))
    fillGrid = fillGrid.set(7, 0, Some(Rook(Color.BLACK)))
    fillGrid = fillGrid.set(0, 7, Some(Rook(Color.WHITE)))
    fillGrid = fillGrid.set(1, 7, Some(Knight(Color.WHITE)))
    fillGrid = fillGrid.set(2, 7, Some(Bishop(Color.WHITE)))
    fillGrid = fillGrid.set(3, 7, Some(Queen(Color.WHITE)))
    fillGrid = fillGrid.set(4, 7, Some(King(Color.WHITE)))
    fillGrid = fillGrid.set(5, 7, Some(Bishop(Color.WHITE)))
    fillGrid = fillGrid.set(6, 7, Some(Knight(Color.WHITE)))
    fillGrid = fillGrid.set(7, 7, Some(Rook(Color.WHITE)))

    for {
      col <- 0 until size
    } fillGrid = fillGrid.set(col, 1, Some(Pawn(Color.BLACK)))

    for {
      col <- 0 until size
    } fillGrid = fillGrid.set(col, 6, Some(Pawn(Color.WHITE)))

    return fillGrid
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