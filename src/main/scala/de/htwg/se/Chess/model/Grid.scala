package de.htwg.se.Chess.model

case class Grid(private val cells:Matrix[Cell]) {

  val size:Int = cells.size

  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))


  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))

  override def toString: String = {
    val lineseparator = "|" + "---+" * (size-1) + "---|\n"
    val line = "| x " * size + "|\n"
    var box = "\n" + (lineseparator + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }
}