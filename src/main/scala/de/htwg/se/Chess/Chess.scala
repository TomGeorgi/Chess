package de.htwg.se.Chess


import de.htwg.se.Chess.model._
import de.htwg.se.Chess.aview._

import scala.io.StdIn.readLine

object Chess {

  var grid = new Grid(8)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    println("Eingabeformat: Figur Spalte Reihe")
    println("Figur: Bauer(B) Turm(T) Läufer(L) Springer(S) König(K) Dame(Q)")
    println("Spalte: von A bis H")
    println("Reihe: von 1 bis 8")

    var input: String = ""

    do {
      println("Grid: " + grid.toString)
      input = readLine()
      grid = tui.processInputLines(input, grid)
    } while (input != "q")
  }
}
