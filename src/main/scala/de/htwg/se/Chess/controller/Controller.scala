package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.Observable

class Controller(var grid: Grid, var player: (Player, Player)) extends Observable {

  def this(grid: Grid, player1: String, player2: String) = this(grid, (Player(player1, Color.WHITE), Player(player2, Color.BLACK)))

  def turn(placeCol: Int, placeRow: Int, newPlaceCol: Int, newPlaceRow: Int): Unit = {
    var whichPlayer: Player = playerAtTurn
    var result: (Grid, Boolean) = grid.turn(placeCol, placeRow, newPlaceCol, newPlaceRow, whichPlayer, grid)
    this.grid = result._1
    notifyObservers
    if (result._2) setNextPlayer
  }

  def playerAtTurn: Player = player._1
  def setNextPlayer: Unit = player = player.swap

  def createEmptyGrid(player: (String, String)): Unit = {
    val grid = new Grid(8).fill()
    this.grid = grid
    this.player = (Player(player._1, Color.WHITE), Player(player._2, Color.BLACK))
    notifyObservers
  }

  def gridToString: String = grid.toString

  def set(col: Int, row: Int, value: String, color: String): Unit = {
    val c = color match {
      case "w" => Color.WHITE
      case "b" => Color.BLACK
      case "_" => Color.EMPTY
    }

    val fig : Figure = value match {
      case "Bauer" | "Pawn" => Pawn(c)
      case "Turm" | "Rook" => Rook(c)
      case "Springer" | "Knight" => Knight(c)
      case "Läufer" | "Bishop" => Bishop(c)
      case "König" | "King" => King(c)
      case "Königin" | "Queen" => Queen(c)
    }
    this.grid = grid.set(col, row, Some(fig))
    notifyObservers
  }
}
