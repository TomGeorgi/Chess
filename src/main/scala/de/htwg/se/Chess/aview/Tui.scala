package de.htwg.se.Chess.aview

import de.htwg.se.Chess.model._

class Tui {
  def processInputLines(input: String, grid: Grid): Grid = {
    input match {
      case "g" => grid
      case "n" => new Grid(8)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case value :: column :: row :: Nil => grid.set(8 - row.toInt, charToValue(column) - 1, value)
          case _ => grid
        }
    }
  }

  def charToValue(col: String): Int = {
    col match {
      case "A" | "a" => 1
      case "B" | "b" => 2
      case "C" | "c" => 3
      case "D" | "d" => 4
      case "E" | "e" => 5
      case "F" | "f" => 6
      case "G" | "g" => 7
      case "H" | "h" => 8

    }
  }
}
