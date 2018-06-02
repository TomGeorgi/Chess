package de.htwg.se.Chess.model

case class Player(name: String, color: Color.Value) {
   override def toString:String = name
}

