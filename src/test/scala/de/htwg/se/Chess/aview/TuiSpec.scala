package de.htwg.se.Chess.aview

import de.htwg.se.Chess.controller.Controller
import de.htwg.se.Chess.model._
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
    "create and empty Chess with Player names 'n name1 name2'" in {
      tui.processInputLine("n name1 name2")
      controller.grid should be (new Grid(8).fill())
    }
    "set a cell on to white at 'a 2 a 4'" in {
      tui.processInputLine("a 2 a 4")
      controller.grid.cell(4, 0).value should be(Some(Pawn(Color.WHITE)))
    }
  }
}
