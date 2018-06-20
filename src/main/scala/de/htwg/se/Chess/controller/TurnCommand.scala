package de.htwg.se.Chess.controller

import de.htwg.se.Chess.model._
import de.htwg.se.Chess.model.GameStatus._
import de.htwg.se.Chess.util.Command

class TurnCommand(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, controller: Controller) extends Command {

  var memento: (Grid, (Player, Player)) = (controller.grid, controller.player)

  override def doStep: Unit = {
    memento = (controller.grid, controller.player)

    val whichPlayer = controller.playerAtTurn
    val oldValue = controller.grid.cell(oldRow, oldCol).value
    var canSet: Boolean = false

    println(controller.grid.isInCheckColor)
    if(controller.grid.isInCheckColor == whichPlayer.color) {
      println("Verscuhe bitte nich check zu bleiben")
    }

    oldValue match {
      case Some(res) => {
        val color = res.color
        if (color == whichPlayer.color) canSet = res.move(oldRow, oldCol, newRow, newCol, controller.grid)
      }
      case None =>
    }

    if (canSet) {
      var gridtoCheck = controller.grid.set(oldRow, oldCol, None)
      gridtoCheck = gridtoCheck.set(newRow, newCol, oldValue)
      if(gridtoCheck.isInCheck(whichPlayer.color)){
        controller.gameStatus = MOVE_NOT_VALID
      }
      else
      {
        if(gridtoCheck.isInCheck(Color.colorReverse(whichPlayer.color))) {
          println("HIER könnte ihre checkmate methode stehen")
          gridtoCheck.isInCheckColor = Color.colorReverse(whichPlayer.color)
          println(gridtoCheck.isInCheckColor)
          controller.gameStatus = CHECK
        }
        controller.grid = gridtoCheck
        controller.setNextPlayer
        controller.gameStatus = NEXT_PLAYER
      }

    } else {
        controller.gameStatus = MOVE_NOT_VALID
    }
  }

  override def undoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }
}
