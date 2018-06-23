package de.htwg.se.Chess.model.fileIoComponent

import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.playerComponent.PlayerInterface

trait FileIOInterface {

  def load: Option[(GridInterface, (PlayerInterface, PlayerInterface))]
  def save(grid: GridInterface, player: (PlayerInterface, PlayerInterface)): Unit

}
