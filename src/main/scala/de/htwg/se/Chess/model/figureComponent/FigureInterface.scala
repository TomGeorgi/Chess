package de.htwg.se.Chess.model.figureComponent

import com.google.inject.assistedinject.Assisted
import com.google.inject.name.Named
import de.htwg.se.Chess.model.figureComponent.figureBaseImpl._
import de.htwg.se.Chess.model.gridComponent.GridInterface

trait Figure {

  @Assisted val color: Color.Value
  val typ: FigureType.Value

  def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: GridInterface): Boolean

  def colorReverse(color: Color.Value): Color.Value

}

trait FigureFactory {

  @Named("King") def createKing(color: Color.Value): King
  @Named("Queen") def createQueen(color: Color.Value): Queen
  @Named("Bishop") def createBishop(color: Color.Value): Bishop
  @Named("Knight") def createKnight(color: Color.Value): Knight
  @Named("Rook") def createRook(color: Color.Value): Rook
  @Named("Pawn") def createPawn(color: Color.Value): Pawn

}

object FigureType extends Enumeration {
  val PAWN, ROOK, KING, QUEEN, KNIGHT, BISHOP = Value

  def fromString(s: String): Option[FigureType.Value] = s.trim match {
    case "PAWN" => Some(PAWN)
    case "ROOK" => Some(ROOK)
    case "KING" => Some(KING)
    case "QUEEN" => Some(QUEEN)
    case "KNIGHT" => Some(KNIGHT)
    case "BISHOP" => Some(BISHOP)
    case _ => None
  }
}

object Color extends Enumeration {
  val EMPTY, BLACK, WHITE = Value

  def colorReverse(color: Color.Value): Color.Value = color match {
      case Color.WHITE => Color.BLACK
      case Color.BLACK => Color.WHITE
      case Color.EMPTY => Color.EMPTY
    }

  def fromString(s: String): Option[Color.Value] = s.trim match {
    case "BLACK" => Some(BLACK)
    case "WHITE" => Some(WHITE)
    case _ => None
  }

}