package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model.GameStatus._
import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.{Observable, UndoManager}

class Controller(var grid: Grid, var player: (Player, Player)) extends Observable {

  var gameStatus: GameStatus = IDLE
  val size: Int = 8
  private val undoManager = new UndoManager

  def this(grid: Grid, player1: String, player2: String) = this(grid, (Player(player1, Color.WHITE), Player(player2, Color.BLACK)))

  def playerAtTurn: Player = player._1
  def setNextPlayer: Unit = player = player.swap

  def createNewGrid(player: (String, String)): Unit = {
    val grid = new Grid(size).fill()
    this.grid = grid
    this.player = (Player(player._1, Color.WHITE), Player(player._2, Color.BLACK))
    notifyObservers
  }

  def createEmptyGrid(player: (String, String)): Unit = {
    val grid = new Grid(size)
    this.grid = grid
    this.player = (Player(player._1, Color.WHITE), Player(player._2, Color.BLACK))
    notifyObservers
  }

  def gridToString: String = grid.toString

  def turn(placeRow: Int, placeCol: Int, newPlaceRow: Int, newPlaceCol: Int): Unit = {
    undoManager.doStep(new TurnCommand(placeRow, placeCol, newPlaceRow, newPlaceCol, this))
    notifyObservers
  }

  def set(row: Int, col: Int, value: String, color: String): Unit = {
    undoManager.doStep(new SetCommand(row, col, value, color, this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }
}