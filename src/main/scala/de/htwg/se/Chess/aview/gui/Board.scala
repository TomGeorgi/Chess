package de.htwg.se.Chess.aview.gui

import de.htwg.se.Chess.model.{FigureType, Grid}
import de.htwg.se.Chess.controller._

import scala.swing._
import scala.swing.{Component, Dimension}
import java.awt.{BasicStroke, Color, Graphics2D, Toolkit}
import java.awt.image.ImageObserver

class Board(val controller: Controller, var componentSize: Dimension) extends Component with ImageObserver{

  componentSize.setSize((componentSize.height * 0.8) toInt, (componentSize.height * 0.8) toInt)
  preferredSize = new Dimension(componentSize.width, componentSize.height)
  def squareSize = new Dimension(componentSize.width / 8, componentSize.height / 8)

  val board = controller.grid
  val field = board.size - 1
  val whiteColor = new Color(211, 139, 68)
  val blackColor = new Color(254, 206, 157)

  override def paintComponent(g: Graphics2D) = {
    listenTo(controller)

    for {
      row <- 0 to field
      col <- 0 to field
    } {
      val isWhite = (row + col) % 2 == 0
      if (isWhite) g.setColor(whiteColor)
      else g.setColor(blackColor)

      val currentPos = new Point(row * squareSize.height, componentSize.width - (col + 1) * squareSize.width)

      g.fillRect(currentPos.y, currentPos.x, squareSize.width, squareSize.height)


/*      controller.grid.cell(row, col).value match {
        case Some(fig) => g.drawImage(FigureImg.forFigures(fig), currentPos.y, currentPos.x, this)
        case None =>
      }
*/

      paintField(g, currentPos, (row, col))
    }
  }

  def paintField(g: Graphics2D, currPos: Point, pos: (Int, Int)) = {
    controller.grid.cell(pos._1, pos._2).value match {
      case None =>
      case Some(res) => {
        //println(pos + " " + res.typ)
        g.drawImage(FigureImg.forFigures(res), currPos.y, currPos.x, this)
      }
    }

  }

  override def imageUpdate(image: Image, i: Int, i1: Int, i2: Int, i3: Int, i4: Int): Boolean = false
}
