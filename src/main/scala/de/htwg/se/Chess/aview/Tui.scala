package de.htwg.se.Chess.aview

import de.htwg.se.Chess.model._

class Tui {
  def processInputLines(input: String, grid: Grid): Grid = {
    input match {
      case "g" => grid
      case "n" => new Grid(8)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case column :: row :: value :: Nil => grid.set(8 - row.toInt, charToValue(column) - 1, value)
          case _ => grid
        }
    }
  }

  def charToValue(col: String): Int = {
    col match {
      case "A" => 1
      case "B" => 2
      case "C" => 3
      case "D" => 4
      case "E" => 5
      case "F" => 6
      case "G" => 7
      case "H" => 8

    }
  }
}
