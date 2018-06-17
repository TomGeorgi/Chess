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
    val in = input.split("[ ]+")
    in(0) match {
      case "q" =>
      case "emp" => {
        if (in.length >= 3) controller.createEmptyGrid((in(1), in(2))) else controller.createEmptyGrid(player)
      }
      case "z" => controller.undo
      case "y" => controller.redo
      case "help" => println("\n q -> Leaves the game\n n -> Start a new Game with Player 1 and Player 2\n n - name - name -> Start a new Game with the entered names for player1 and player2\n emp -> Start a new Game with an empty Grid\n emp - name - name -> Start a new Game with an empty Grid and the entered names for player1 and player2 \n")
      case "set" => processInputMove(in)
      case "n" => {
        if (in.length >= 3) controller.createNewGrid((in(1), in(2))) else controller.createNewGrid(player)
        println("Input format: column - row - new column - new row\n column: from A to H\n Row: from 1 to 8\n For More Information type help")
        update
      }
      case _ => processInputMove(in)
    }
  }

  def processInputMove(in: Array[String]): Unit = {
    //noinspection ScalaStyle
    in.toList.filter(c => c != " ").map(c => c.toString) match {
      case placeCol :: placeRow :: newPlaceCol:: newPlaceRow :: Nil =>
        controller.turn(8 - placeRow.toInt, charToValue(placeCol),8 - newPlaceRow.toInt, charToValue(newPlaceCol))
      case _ :: pC :: pR :: value :: color :: Nil => controller.set(8 - pR.toInt, charToValue(pC),  value, color)
      case _ :: pC :: pR :: Nil => controller.set(8 - pR.toInt, charToValue(pC), "_", "_")
      case _ =>
    }
  }

  def charToValue(col: String): Int = {
    col match {
      case "A" | "a" => 0
      case "B" | "b" => 1
      case "C" | "c" => 2
      case "D" | "d" => 3
      case "E" | "e" => 4
      case "F" | "f" => 5
      case "G" | "g" => 6
      case "H" | "h" => 7
    }
  }

  override def update: Unit = {
    println(controller.gridToString)
    if (controller.gameStatus == NEXT_PLAYER) {
      println("\n" + controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus))
    } else {
      println("\n" + controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus))
    }

  }
}
