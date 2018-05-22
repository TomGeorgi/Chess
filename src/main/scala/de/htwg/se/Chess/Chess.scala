package de.htwg.se.Chess


import de.htwg.se.Chess.model._
import de.htwg.se.Chess.aview._

import scala.io.StdIn.readLine

object Chess {

  var grid = new Grid(8)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    println("Eingabeformat: row column")

    var input: String = ""

    do {
      println("Grid: " + grid.toString)
      input = readLine()
      grid = tui.processInputLines(input, grid)
    } while (input != "q")
  }
}
