package de.htwg.se.Chess


import de.htwg.se.Chess.model._
import de.htwg.se.Chess.aview._
import de.htwg.se.Chess.aview.gui.SwingGui
import de.htwg.se.Chess.controller.Controller

import scala.io.StdIn.readLine

object Chess {

  val defaultSize = 8

  var controller = new Controller(new Grid(defaultSize).fill(), "Player 1", "Player 2")
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)


  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input.trim())
    } while (input != "q")
  }
}
