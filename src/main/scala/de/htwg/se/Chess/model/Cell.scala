package de.htwg.se.Chess.model

case class Cell(value: Option[Figure]) {

  def isSet:Boolean = value match {
    case Some(_) => true
    case None => false
  }

  def getValue: Option[Figure] = value

  override def toString: String = {
    /*
    this match {
      case Cell(, Color.EMPTY) => " "
      case Cell(FigureType.PAWN, Color.WHITE) => "♙"
      case Cell(FigureType.KNIGHT, Color.WHITE) => "♘"
      case Cell(FigureType.BISHOP, Color.WHITE) => "♗"
      case Cell(FigureType.ROOK, Color.WHITE) => "♖"
      case Cell(FigureType.QUEEN, Color.WHITE) => "♕"
      case Cell(FigureType.KING, Color.WHITE) => "♔"
      case Cell(FigureType.PAWN, Color.BLACK) => "♟"
      case Cell(FigureType.KNIGHT, Color.BLACK) => "♞"
      case Cell(FigureType.BISHOP, Color.BLACK) => "♝"
      case Cell(FigureType.ROOK, Color.BLACK) => "♜"
      case Cell(FigureType.QUEEN, Color.BLACK) => "♛"
      case Cell(FigureType.KING, Color.WHITE) => "♚"
    }
      */
    value match {
      case None => " "
      case Some(s) => s.toString
    }
  }
}



