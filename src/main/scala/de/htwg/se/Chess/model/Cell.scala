package de.htwg.se.Chess.model

case class Cell(value:Int) {
  def isSet:Boolean = value != 0
}
