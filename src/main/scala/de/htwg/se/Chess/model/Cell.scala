package de.htwg.se.Chess.model

case class Cell(value:String) {
  def isSet:Boolean = value != 0.toString

  override def toString: String = {
    value
  }
}
