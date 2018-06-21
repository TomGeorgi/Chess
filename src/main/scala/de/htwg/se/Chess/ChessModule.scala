package de.htwg.se.Chess

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Chess.controller.controllerComponent._
import de.htwg.se.Chess.model.gridComponent.{CellFactory, CellInterface, GridFactory, GridInterface}
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.{Cell, Grid}
import de.htwg.se.Chess.model.playerComponent.{PlayerFactory, PlayerInterface}
import de.htwg.se.Chess.model.playerComponent.playerBaseImpl.Player

class ChessModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 8
  val defaultPlayerName = ("Player 1", "Player 2")

  override def configure(): Unit = {
    install(new FactoryModuleBuilder().implement(classOf[PlayerInterface], classOf[Player]).build(classOf[PlayerFactory]))
    install(new FactoryModuleBuilder().implement(classOf[GridInterface], classOf[Grid]).build(classOf[GridFactory]))
    install(new FactoryModuleBuilder().implement(classOf[ControllerInterface], classOf[controllerBaseImpl.Controller]).build(classOf[ControllerFactory]))
    install(new FactoryModuleBuilder().implement(classOf[CellInterface], classOf[Cell]).build(classOf[CellFactory]))
    bind[CellInterface].to[Cell]
  }
}
