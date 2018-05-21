package de.htwg.se.Chess

import de.htwg.se.Chess.model.Player

object Chess {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
