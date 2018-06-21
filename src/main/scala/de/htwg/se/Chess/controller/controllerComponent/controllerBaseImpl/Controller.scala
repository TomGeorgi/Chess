package de.htwg.se.Chess.controller.controllerComponent.controllerBaseImpl

import com.google.inject.Guice
import com.google.inject.assistedinject.{Assisted, AssistedInject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.Chess.ChessModule
import de.htwg.se.Chess.controller.controllerComponent.GameStatus.{GameStatus, _}
import de.htwg.se.Chess.controller.controllerComponent.{ControllerInterface, Played}
import de.htwg.se.Chess.model.figureComponent.Color
import de.htwg.se.Chess.model.gridComponent.{GridFactory, GridInterface}
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Chess.model.playerComponent.{PlayerFactory, PlayerInterface}
import de.htwg.se.Chess.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.Chess.util.UndoManager

import scala.swing.Publisher

class Controller @AssistedInject() (@Assisted var grid: GridInterface, @Assisted var player: (PlayerInterface, PlayerInterface)) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  val size: Int = 8
  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new ChessModule)

  def this(grid: GridInterface, player1: String, player2: String) = this(grid, (Player(player1, Color.WHITE), Player(player2, Color.BLACK)))

  def playerAtTurn: PlayerInterface = player._1
  def setNextPlayer: Unit = player = player.swap

  def createNewGrid(player: (String, String)): Unit = {
    val grid = injector.instance[GridFactory].create(size).fill()
    this.grid = grid
    this.player = (injector.instance[PlayerFactory].create(player._1, Color.WHITE), injector.instance[PlayerFactory].create(player._2, Color.BLACK))
    publish(new Played)
  }

  def createEmptyGrid(player: (String, String)): Unit = {
    val grid =  injector.instance[GridFactory].create(size)
    this.grid = grid
    this.player = (injector.instance[PlayerFactory].create(player._1, Color.WHITE), injector.instance[PlayerFactory].create(player._2, Color.BLACK))
    publish(new Played)
  }

  def gridToString: String = grid.toString

  def playerAtTurnToString: String = playerAtTurn.name

  def turn(placeRow: Int, placeCol: Int, newPlaceRow: Int, newPlaceCol: Int): Unit = {
    undoManager.doStep(new TurnCommand(placeRow, placeCol, newPlaceRow, newPlaceCol, this))
    publish(new Played)
  }

  def set(row: Int, col: Int, value: String, color: String): Unit = {
    undoManager.doStep(new SetCommand(row, col, value, color, this))
    publish(new Played)
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new Played)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new Played)
  }
}