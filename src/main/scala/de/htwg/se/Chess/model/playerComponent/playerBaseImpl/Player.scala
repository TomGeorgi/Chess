package de.htwg.se.Chess.model.playerComponent.playerBaseImpl

import de.htwg.se.Chess.model.figureComponent.Color
import de.htwg.se.Chess.model.playerComponent.PlayerInterface

case class Player(name: String, color: Color.Value) extends PlayerInterface{
   override def toString:String = name
}

