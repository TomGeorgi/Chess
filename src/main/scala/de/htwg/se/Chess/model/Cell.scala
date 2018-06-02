package de.htwg.se.Chess.model

case class Cell(value: Option[Figure]) {

  def isSet:Boolean = value match {
    case Some(_) => true
    case None => false
  }

  def getValue: Option[Figure] = value

  override def toString: String = {
    value match {
      case Some(s) => s.toString
      case None => " "
    }
  }
}



