package de.htwg.se.Chess.aview

import de.htwg.se.Chess.model._

class Tui {
  def processInputLines(input: String, grid: Grid): Grid = {
    input match {
      case "g" => grid
      case "n" => new Grid(8)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case row :: column :: value :: Nil => grid.set(row.toInt - 1, column.toInt - 1, value)
          case _ => grid
        }
    }
  }
}
