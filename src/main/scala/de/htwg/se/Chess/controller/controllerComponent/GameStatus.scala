package de.htwg.se.Chess.controller.controllerComponent

object GameStatus extends Enumeration {
  type GameStatus = Value

  val IDLE, NEXT_PLAYER, MOVE_NOT_VALID, CHECK_MATE, CASTLING, EN_PASSANT, CHECK, PROMOTION, NO_FIGURE = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEXT_PLAYER -> " is at turn",
    MOVE_NOT_VALID -> ", this move is not valid!",
    CHECK_MATE -> " is CHECKMATE!",
    CASTLING -> " did a Castling!",
    EN_PASSANT -> " did en passant!",
    CHECK -> " is in check...",
    PROMOTION -> " got a Promotion!",
    NO_FIGURE -> " there is no figure on this spot"
  )

  def message(msg: GameStatus) = map(msg)
}
