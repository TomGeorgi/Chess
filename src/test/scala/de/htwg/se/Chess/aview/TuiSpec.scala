package de.htwg.se.Chess.aview

import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model._
import de.htwg.se.Chess.util.UndoManager
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {


  "A Chess Tui" should {
    val controller = new Controller(new Grid(8), "Player 1", "Player 2")
    val tui = new Tui(controller)
    "create and empty Chess on input 'n'" in {
      tui.processInputLine("n")
      controller.grid should be(new Grid(8).fill())
    }
    "set a cell on input 'set a 2 Bauer w'" in {
      tui.processInputLine("set a 2 Rook w")
      controller.grid.cell(6, 0).value should be(Some(Rook(Color.WHITE)))
    }
    "set an empty cell on input 'set a 2'" in {
      tui.processInputLine("set a 2")
      controller.grid.cell(6, 0).value should be(None)
    }
    "create and empty Chess with Player names 'n name1 name2'" in {
      tui.processInputLine("n name1 name2")
      controller.grid should be (new Grid(8).fill())
    }
    "set a cell on white at 'a 2 a 4'" in {
      tui.processInputLine("a 2 a 4")
      controller.grid.cell(4, 0).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'b 2 b 4'" in {
      tui.processInputLine("b 2 b 4")
      controller.grid.cell(4, 1).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'c 2 c 4'" in {
      tui.processInputLine("c 2 c 4")
      controller.grid.cell(4, 2).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'd 2 d 4'" in {
      tui.processInputLine("d 2 d 4")
      controller.grid.cell(4, 3).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'e 2 e 4'" in {
      tui.processInputLine("e 2 e 4")
      controller.grid.cell(4, 4).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'f 2 f 4'" in {
      tui.processInputLine("f 2 f 4")
      controller.grid.cell(4, 5).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
    }
    "set a cell on to white at 'g 2 g 4'" in {
      tui.processInputLine("g 2 g 4")
      controller.grid.cell(4, 6).value should be(Some(Pawn(Color.WHITE)))
      tui.processInputLine("z")
      controller.grid.cell(4, 6).value should be(None)
      tui.processInputLine("y")
      controller.grid.cell(4, 6).value should be(Some(Pawn(Color.WHITE)))
    }
    "set a cell on to white at 'h 2 h 4'" in {
      tui.processInputLine("z")
      tui.processInputLine("h 2 h 4")
      controller.grid.cell(4, 7).value should be(Some(Pawn(Color.WHITE)))

    }
  }
}
