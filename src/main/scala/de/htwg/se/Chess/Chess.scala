package de.htwg.se.Chess


import de.htwg.se.Chess.model._
import de.htwg.se.Chess.aview._
import de.htwg.se.Chess.controller.Controller

import scala.io.StdIn.readLine

object Chess {

  var controller = new Controller(new Grid(8), "Player 1", "Player 2")
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
