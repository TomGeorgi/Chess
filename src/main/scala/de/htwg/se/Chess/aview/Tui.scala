package de.htwg.se.Chess.aview

import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model.GameStatus
import de.htwg.se.Chess.model.GameStatus._
import de.htwg.se.Chess.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  val player: (String, String) = ("Player 1", "Player 2")

  println("Chess from Tom Georgi and Lukas Rohloff")
  println("Input format: column - row - new column - new row")
  println("column: from A to H")
  println("Row: from 1 to 8")
  println("For More Information type help")
  update


  def processInputLine(input: String): Unit = {
    val in = input.split(" ")
    in(0) match {
      case "help" => println("up -> prints the Grid again\n" +
        "q -> Leaves the game\n" +
        "n -> Start a new Game with Player 1 and Player 2\n" +
        "n - name - name -> Start a new Game with the entered names for player1 and player2\n")
      case "up" => update
      case "q" =>
      case "n" => {
        if (input.length >= 3) controller.createEmptyGrid((in(1), in(2))) else controller.createEmptyGrid(player)
        println("Input format: column - row - new column - new row\n" +
          "column: from A to H\n" +
          "Row: from 1 to 8\n" +
          "For More Information type help")
        update
      }
      case "z" => controller.undo
      case "y" => controller.redo
      case "set" => trySet(in)
      case _ => {
        processInputMove(in)

      }
    }
  }

  def trySet(in: Array[String]): Unit = {
    in.toList.filter(c => c != " ").map(c => c.toString) match {
      case _ :: pC :: pR :: value :: color :: Nil => controller.set(charToValue(pC)-1, 8 - pR.toInt, value, color)
    }
  }

  def processInputMove(in: Array[String]): Unit = {
    //noinspection ScalaStyle
    in.toList.filter(c => c != " ").map(c => c.toString) match {
      case placeCol :: placeRow :: newPlaceCol:: newPlaceRow :: Nil =>
        controller.turn(charToValue(placeCol) - 1, 8 - placeRow.toInt , charToValue(newPlaceCol) - 1, 8 - newPlaceRow.toInt)
      case _ =>
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

  override def update: Unit = {
    println(controller.gridToString)
    if (controller.gameStatus == NEXT_PLAYER) {
      println(controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus))
    } else {
      println(controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus))
    }

  }
}
