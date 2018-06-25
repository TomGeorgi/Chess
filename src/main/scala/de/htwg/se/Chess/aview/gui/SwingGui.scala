package de.htwg.se.Chess.aview.gui

import java.awt.Toolkit

import scala.swing._
import scala.swing.event._
import de.htwg.se.Chess.controller.controllerComponent.GameStatus._
import de.htwg.se.Chess.controller.controllerComponent.{ControllerInterface, GridSizeChanged, Played, GameStatus}

import scala.swing.event.MousePressed
import scala.swing.event.MouseDragged

class CellClicked(val row: Int, val col: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame {

  val player: (String, String) = ("Player 1", "Player 2")

  var screenSize = Toolkit.getDefaultToolkit.getScreenSize
  var windowSize = ((screenSize.width min screenSize.height) * 0.8).toInt
  //preferredSize = new Dimension(windowSize, windowSize)
  preferredSize = new Dimension(600, 600)
  listenTo(controller)
  var set = 0

  menuBar = new MenuBar {
    val menu = new Menu("File") {
      mnemonic = Key.F
      visible_=(true)

      contents += new MenuItem(Action("Empty") {
        controller.createEmptyGrid(player._1, player._2)
      })
      contents += new MenuItem(Action("Save") {
        controller.save
      })
      contents += new MenuItem(Action("Load") {
        controller.load
      })

      val admin = new MenuItem(Action("Admin") {
        visible_=(true)
        listenTo(this.mouse.clicks)
        reactions += {
          case MousePressed(_, _, _, _, _) => adminmenu.visible_=(true)
        }
      })
      contents += admin
    }

    val adminmenu = new Menu("Admin") {
      visible_=(false)
      contents += new Menu("Set") {
        contents += new Menu("King") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "King", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "King", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        contents += new Menu("Queen") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Queen", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Queen", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        contents += new Menu("Bishop") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Bishop", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Bishop", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        contents += new Menu("Knight") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Knight", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Knight", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        contents += new Menu("Rook") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Rook", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Rook", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        contents += new Menu("Pawn") {
          contents += new MenuItem(Action("Black") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Pawn", "b")
            board.Coord(-1, -1)
            set = 0
          })
          contents += new MenuItem(Action("White") {
            val coord = getOldCoord()
            controller.set(7 - coord._1, coord._2, "Pawn", "w")
            board.Coord(-1, -1)
            set = 0
          })
        }
        listenTo(this.mouse.clicks)
        reactions += {
          case MousePressed(_, _, _, _, _) => set = 1
        }
      }
      contents += new MenuItem(Action("None") {
        if(set != 2) {
          set = 2
          labelpanel.label2.text = "Sie befinden sich im None bzw delete mode \n um dies zu deaktivieren klicken sie unter Admin erneut auf None"
        } else {
          set = 0
          labelpanel.label2.text = ""
        }
      })
    }

    contents += menu
    contents += adminmenu
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

  val labelpanel = new GridPanel(2, 1) {
    val label = new Label("Willkommen")
    contents += label
    val label2 = new Label("")
    contents += label2
  }
  
  reactions += {
    case event: GridSizeChanged =>
    case event: Played => repaint()
      labelpanel.label.text = controller.playerAtTurn.name + " is at turn"
      if (controller.gameStatus == NEXT_PLAYER) {
        labelpanel.label2.text = ""
      } else {
        labelpanel.label2.text = "\n" + controller.playerAtTurn.toString + GameStatus.message(controller.gameStatus)
      }
  }

  def mouseClick(x: Int, y: Int, dimension: Dimension): Unit = {
    println(set)
    val gridSize = controller.grid.size - 1
    var stepcol = dimension.width / 8
    var steprow = dimension.height / 8
    var col = 0
    var row = dimension.height
    if(set == 1) {
      val coord = getCoord(x, y, row, col, steprow, stepcol)
      save(coord._1, coord._2)
      board.Coord(coord._1, coord._2)
    } else if(set == 2) {
      val coord = getCoord(x, y, row, col, steprow, stepcol)
      //controller.grid = controller.grid.set(coord._1, coord._2, None)
    } else if(getClicks() == 1) {
        val coord = getCoord(x, y, row, col, steprow, stepcol)
        val oldcoord = getOldCoord()
        setClicks(2)
        board.Coord(-1, -1)
        controller.turn(gridSize - oldcoord._1, oldcoord._2, gridSize - coord._1, coord._2)
      } else {
        val coord = getCoord(x, y, row, col, steprow, stepcol)
        controller.grid.cell(gridSize - coord._1, coord._2).value match {
          case Some(res) => if(res.color == controller.playerAtTurn.color) {
            board.Coord(coord._1, coord._2)
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
    val lines = /*controller.grid.size - 1*/ 7
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

  contents = new BorderPanel {
    add(labelpanel, BorderPanel.Position.North)
    add(panel, BorderPanel.Position.Center)
  }

  title = "HTWG Chess"
  resizable = true
  visible = true
}
