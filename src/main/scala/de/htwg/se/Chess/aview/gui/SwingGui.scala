package de.htwg.se.Chess.aview.gui

import java.awt.Toolkit

import scala.swing._
import scala.swing.event._
import de.htwg.se.Chess.controller._
import de.htwg.se.Chess.model.GameStatus._
import de.htwg.se.Chess.model.GameStatus

import scala.swing.event.MousePressed

class CellClicked(val row: Int, val col: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  val player: (String, String) = ("Player 1", "Player 2")

  var screenSize = Toolkit.getDefaultToolkit.getScreenSize
  var windowSize = ((screenSize.width min screenSize.height) * 0.8).toInt
  //preferredSize = new Dimension(windowSize, windowSize)
  preferredSize = new Dimension(600, 600)
  listenTo(controller)

  menuBar = new MenuBar {
    val menu = new Menu("File") {
        mnemonic = Key.F
        visible_=(true)

        contents += new MenuItem(Action("Empty") { controller.createEmptyGrid(player._1, player._2) })
        contents += new Menu("Set") {
          contents += new Menu("King") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "King", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "King", "w")})
          }
          contents += new Menu("Queen") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Queen", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Queen", "w")})
          }
          contents += new Menu("Bishop") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Bishop", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Bishop", "w")})
          }
          contents += new Menu("Knight") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Knight", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Knight", "w")})
          }
          contents += new Menu("Rook") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Rook", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Rook", "w")})
          }
          contents += new Menu("Pawn") {
            contents += new MenuItem(Action("Black") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Pawn", "b")})
            contents += new MenuItem(Action("White") { val c: Option[String] = Dialog.showInput(null, "a-h 1-8", initial = "")
              setcommand(c, "Pawn", "w")})
          }
        }
      }

  contents += menu
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
      contents += new MenuItem(Action("Exit") { sys.exit(0) })
    }
  }

  var board = new Board(controller, preferredSize)

  val panel = new FlowPanel() {
    contents += new BoxPanel(Orientation.Vertical) {
      listenTo(this.mouse.clicks)
      contents += board
      reactions += {
        case MousePressed(_, point, _, _, _) =>
          mouseClick(point.x, point.y, this.size)
      }

    }
  }

  val labelpanel = new FlowPanel() {
    val label = new Label("Willkommen")
    label.horizontalAlignment = Alignment.Center
    contents += label
  }

  val labelpanel2 = new FlowPanel() {
    val label = new Label("")
    label.horizontalAlignment = Alignment.Center
    contents += label
  }


  reactions += {
    case event: GridSizeChanged =>
    case event: Played => repaint()
      labelpanel.label.text = controller.playerAtTurn.name + " is at turn"
      if (controller.gameStatus == NEXT_PLAYER) {
        labelpanel2.label.text = ""
      } else {
        labelpanel2.label.text = "\n" + controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus)
      }
  }

  def mouseClick(x: Int, y: Int, dimension: Dimension): Unit = {
    val gridSize = controller.grid.size - 1
    var stepcol = dimension.width / 8
    var steprow = dimension.height / 8
    var col = 0
    var row = dimension.height
    if(getClicks() == 1) {
      val coord = getCoord(x, y, row, col, steprow, stepcol)
      val oldcoord = getOldCoord()
      setClicks(2)
      controller.turn(gridSize - oldcoord._1, oldcoord._2, gridSize - coord._1, coord._2)
    } else {
      val coord = getCoord(x, y, row, col, steprow, stepcol)
      controller.grid.cell(gridSize - coord._1, coord._2).value match {
        case Some(res) => if(res.color == controller.playerAtTurn.color) {
            setClicks(1)
            save(coord._1, coord._2)
          } else {
            setClicks(2)
          }
        case None => setClicks(2)
      }
    }
  }


  def getCoord(xCoord: Int, yCoord: Int, row: Int, col: Int, steprow: Int, stepcol: Int): (Int, Int) = {
    val lines = controller.grid.size - 1
    var fieldrow = -1
    var fieldcol = -1
    var rowc = row
    var colc = col
    for(a <- 0 to lines) {
      if(colc <= xCoord && xCoord <= colc + stepcol) {
        fieldcol = a
      }
      colc += stepcol
    }
    for(a <- 0 to lines) {
      if(rowc >= yCoord && yCoord >= rowc - steprow) {
        fieldrow = a
      }
      rowc -= steprow
    }
    return (fieldrow, fieldcol)
  }

  var clickscounter = 0
  def setClicks(x: Int): Unit = {
    clickscounter += x
    if(clickscounter > 1) {
      clickscounter = 0
    }
  }

  def getClicks(): Int = {
    return clickscounter
  }

  var xold = 0
  var yold = 0
  def save(x: Int, y: Int): Unit = {
    xold = x
    yold = y
  }

  def getOldCoord(): (Int, Int) = {
    return (xold, yold)
  }

  var x = ""
  def setcommand(c: Option[String], figuretype: String, color: String): Unit = {
    c match {
      case  Some(s) => x = s.trim()
      case None =>
    }
    val in = x.split("[ ]+")
    if(in.length == 2) {
      controller.set(8 - in(1).toInt, charToValue(in(0)), figuretype, color)
    } else {
      labelpanel2.label.text = "Wrong input for Set"
    }
  }
  def charToValue(col: String): Int = {
    col match {
      case "A" | "a" => 0
      case "B" | "b" => 1
      case "C" | "c" => 2
      case "D" | "d" => 3
      case "E" | "e" => 4
      case "F" | "f" => 5
      case "G" | "g" => 6
      case "H" | "h" => 7
    }
  }

  contents = new BorderPanel {
    add(labelpanel, BorderPanel.Position.North)
    add(labelpanel2, BorderPanel.Position.Center)
    add(panel, BorderPanel.Position.South)
  }

  title = "HTWG Chess"
  resizable = true
  visible = true
}
