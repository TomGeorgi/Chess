package de.htwg.se.Chess.aview.gui

import java.awt.Toolkit

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.Chess.controller._
import de.htwg.se.Chess.util.Observer

import scala.swing.event.MouseClicked
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import scala.io.Source._

class CellClicked(val row: Int, val col: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  var screenSize = Toolkit.getDefaultToolkit.getScreenSize
  var windowSize = ((screenSize.width min screenSize.height) * 0.8).toInt
  preferredSize = new Dimension(windowSize, windowSize)
  listenTo(controller)

  menuBar = new MenuBar {
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
  }

  val board = new Board(controller, preferredSize)

  val panel = new FlowPanel() {

    contents += new BoxPanel(Orientation.Vertical) {
      listenTo(this.mouse.clicks)
      contents += board
      /*reactions += {
        case MousePressed(com, point, _, _, _) =>
          mouseCl
      }*/
    }
  }

  reactions += {
    case event: GridSizeChanged =>
    case event: Played => repaint()
  }

  contents = new BorderPanel {
    add(panel, BorderPanel.Position.North)
  }

  title = "HTWG Chess"
  resizable = true
  visible = true
}
