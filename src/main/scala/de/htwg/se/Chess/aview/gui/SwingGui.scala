package de.htwg.se.Chess.aview.gui

import java.awt.Toolkit

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.Chess.controller._
import de.htwg.se.Chess.model.GameStatus
import de.htwg.se.Chess.util.Observer
import de.htwg.se.Chess.model.Grid

import scala.swing.event.MouseClicked
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import scala.io.Source._

class CellClicked(val row: Int, val col: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  val player: (String, String) = ("Player 1", "Player 2")

  var screenSize = Toolkit.getDefaultToolkit.getScreenSize
  //var windowSize = ((screenSize.width min screenSize.height) * 0.8).toInt
  //preferredSize = new Dimension(windowSize, windowSize)
  var WindowSize = 500
  preferredSize = new Dimension(1300, 1300)
  listenTo(controller)

  menuBar = new MenuBar {
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
      contents += new MenuItem(Action("Exit") { sys.exit(0) })
    }
  }

  val board = new Board(controller, preferredSize)

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

  val textpanel = new FlowPanel() {
    val messages = new TextArea(rows = 10, columns = 10)
    contents += messages
    messages.editable_=(false)

    val outputTexScrollPane = new ScrollPane(messages)
    contents += outputTexScrollPane
  }

  val gridpanel = new GridPanel(3, 1) {
    object eingabe1 extends TextField(columns = 5)

    val label = new Label("Eingabe für Position der Figur, die Sie gerne Bewegen möchten")
    val label2 = new Label("oder verwenden sie die Maus zum Bewegen der Spieler")


    contents += label
    contents += label2
    contents += eingabe1


    listenTo(eingabe1)
    reactions += {
      case EditDone(`eingabe1`) => {
        val in = eingabe1.text.split("[ ]+")
        in(0) match {
          case "emp" => {
            if (in.length >= 3) controller.createEmptyGrid((in(1), in(2))) else controller.createEmptyGrid(player)
          }
          case "help" => textpanel.messages.append("\n q -> Leaves the game\n n -> Start a new Game with Player 1 and Player 2\n n - name - name -> Start a new Game with the entered names for player1 and player2\n emp -> Start a new Game with an empty Grid\n emp - name - name -> Start a new Game with an empty Grid and the entered names for player1 and player2 \n")
          case "n" => {
            if (in.length >= 3) controller.createNewGrid((in(1), in(2))) else controller.createNewGrid(player)
            textpanel.messages.append("Input format: column - row - new column - new row\n column: from A to H\n Row: from 1 to 8\n For More Information type help \n")
          }
          case "set" => processInputMove(in)
          case _ => processInputMove(in)
            eingabe1.text_=("".toString)
        }
      }
    }

  }


  reactions += {
    case event: GridSizeChanged =>
    case event: Played => repaint()
  }

  val secondpanel = new GridPanel(2, 1) {
    contents += textpanel
    contents += gridpanel
  }

  contents = new BorderPanel {
    add(panel, BorderPanel.Position.West)
    add(secondpanel, BorderPanel.Position.East)
  }

  title = "HTWG Chess"
  resizable = true
  visible = true

  def processInputMove(in: Array[String]): Unit = {
    //noinspection ScalaStyle
    in.toList.filter(c => c != " ").map(c => c.toString) match {
      case placeCol :: placeRow :: newPlaceCol:: newPlaceRow :: Nil =>
        //println(8 - placeRow.toInt, charToValue(placeCol),8 - newPlaceRow.toInt, charToValue(newPlaceCol))
        controller.turn(8 - placeRow.toInt, charToValue(placeCol),8 - newPlaceRow.toInt, charToValue(newPlaceCol))
      case _ :: pC :: pR :: value :: color :: Nil => controller.set(8 - pR.toInt, charToValue(pC),  value, color)
      case _ :: pC :: pR :: Nil => controller.set(8 - pR.toInt, charToValue(pC), "_", "_")
      case _ =>
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

  def mouseClick(x: Int, y: Int, dimension: Dimension): Unit = {
    val gridSize = controller.grid.size - 1
    //println(x, y, dimension)
    var fieldcol = 0
    var fieldrow = 0
    var stepcol = dimension.width / 8
    var steprow = dimension.height / 8
    //println(row, col)
    var col = 0
    var row = dimension.height
    val lines = controller.grid.size - 1
    val clicks = getClicks()
    if(clicks == 1) {
      for(a <- 0 to lines) {
        if(col <= x && x <= col + stepcol) {
          fieldcol = a
        }
        col += stepcol
      }
      for(a <- 0 to lines) {
        if(row >= y && y >= row - steprow) {
          fieldrow = a
        }
        row -= steprow
      }
      val coord = getOldCoord()
      setClicks(1)
      println(gridSize - coord._1, gridSize - coord._2, gridSize - fieldrow, gridSize - fieldcol)
      controller.turn(gridSize - coord._1, gridSize - coord._2, gridSize - fieldrow, gridSize - fieldcol)

    } else {
      for(a <- 0 to lines) {
        if(col <= x && x <= col + stepcol) {
          fieldcol = a
        }
        col += stepcol
      }
      for(a <- 0 to lines) {
        if(row >= y && y >= row - steprow) {
          fieldrow = a
        }
        row -= steprow
      }
      if(controller.grid.cell(gridSize- fieldrow, gridSize - fieldcol).isSet) {
        setClicks(1)
        save(fieldrow, fieldcol)
      } else {
       setClicks(3)
      }
    }
  }

  var Clicks = 0
  def setClicks(x: Int): Unit = {
    Clicks += x
    if(Clicks > 1) {
      Clicks = 0
    }
  }

  def getClicks(): Int = {
    return Clicks
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
}
