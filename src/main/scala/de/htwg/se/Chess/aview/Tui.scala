package de.htwg.se.Chess.aview

import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  val player: (String, String) = ("Player 1", "Player 2")

  println("Eingabeformat: Figur Spalte Reihe")
  println("Figur: Bauer(B) Turm(T) Läufer(L) Springer(S) König(K) Dame(Q)")
  println("Spalte: von A bis H")
  println("Reihe: von 1 bis 8")
  update


  def processInputLine(input: String): Unit = {
    val in = input.split(" ")
    in(0) match {
      case "up" => update
      case "q" =>
      case "n" => {
        if (input.length >= 3) controller.createEmptyGrid((in(1), in(2))) else controller.createEmptyGrid(player)
      }
      case "set" => trySet(in)
      case _ => {
        processInputMove(in)

      }
    }
  }

  def trySet(in: Array[String]): Unit = {
    in.toList.filter(c => c != " ").map(c => c.toString) match {
      case set :: pC :: pR :: value :: color :: Nil => controller.set(charToValue(pC)-1, 8 - pR.toInt, value, color)
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
  }
}
