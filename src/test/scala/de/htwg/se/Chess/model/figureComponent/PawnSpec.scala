package de.htwg.se.Chess.model.figureComponent

import de.htwg.se.Chess.model.figureComponent.figureBaseImpl.Pawn
import de.htwg.se.Chess.model.gridComponent.GridInterface
import de.htwg.se.Chess.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnSpec extends WordSpec with Matchers {
  var wgrid: GridInterface = new Grid(8)
  var bgrid: GridInterface = new Grid(8)

  "A Pawn" when {
    "set a correct white move" should {
      wgrid = wgrid.set(6, 1, Some(Pawn(Color.WHITE)))
      "have return true" in {
        Pawn(Color.WHITE).move(6, 1, 5, 1, wgrid) should be(true)
      }
    }

    "set a invalid white move" should {
      wgrid = wgrid.set(6, 0, Some(Pawn(Color.WHITE)))
      wgrid = wgrid.set(5, 0, Some(Pawn(Color.WHITE)))
      "have return false" in {
        Pawn(Color.WHITE).move(6, 0, 5, 0, wgrid) should be(false)
      }
    }

    "set a correct white double jump move" should {
      wgrid = wgrid.set(6, 3, Some(Pawn(Color.WHITE)))
      "have return true" in {
        Pawn(Color.WHITE).move(6, 3, 4, 3, wgrid) should be(true)
      }
    }

    "set a invalid white double jump move" should {
      wgrid = wgrid.set(6, 4, Some(Pawn(Color.WHITE)))
      wgrid = wgrid.set(4, 4, Some(Pawn(Color.WHITE)))
      "have return false" in {
        Pawn(Color.WHITE).move(6, 4, 4, 4, wgrid) should be(false)
      }
      wgrid = wgrid.set(4, 4, None)
      wgrid = wgrid.set(5, 4, Some(Pawn(Color.WHITE)))
      "have return false too" in {
        Pawn(Color.WHITE).move(6, 4, 4, 4, wgrid) should be(false)
      }
    }

    "white can beat" should {
      wgrid = wgrid.set(3, 4, Some(Pawn(Color.WHITE)))
      wgrid = wgrid.set(2, 3, Some(Pawn(Color.BLACK)))
      wgrid = wgrid.set(2, 5, Some(Pawn(Color.WHITE)))
      "have return true" in {
        Pawn(Color.WHITE).move(3, 4, 2, 3, wgrid) should be(true)
      }
      "have return false" in {
        Pawn(Color.WHITE).move(3, 4, 2, 5, wgrid) should be(false)
      }
      "white cant beat on an empty cell" should {
        wgrid = wgrid.set(2, 0, Some(Pawn(Color.WHITE)))
        "have return false" in {
          Pawn(Color.WHITE).move(2, 0, 1, 1, wgrid) should be(false)
        }
      }
    }
    //
    "set a correct black move" should {
      bgrid = bgrid.set(1, 1, Some(Pawn(Color.BLACK)))
      "have return true" in {
        Pawn(Color.BLACK).move(1, 1, 2, 1, bgrid) should be(true)
      }
    }

    "set a invalid black move" should {
      bgrid = bgrid.set(1, 0, Some(Pawn(Color.BLACK)))
      bgrid = bgrid.set(2, 0, Some(Pawn(Color.BLACK)))
      "have return false" in {
        Pawn(Color.BLACK).move(1, 0, 2, 0, bgrid) should be(false)
      }
    }

    "set a correct black double jump move" should {
      bgrid = bgrid.set(1, 3, Some(Pawn(Color.WHITE)))
      "have return true" in {
        Pawn(Color.BLACK).move(1, 3, 3, 3, bgrid) should be(true)
      }
    }

    "set a invalid black double jump move" should {
      bgrid = bgrid.set(1, 4, Some(Pawn(Color.BLACK)))
      bgrid = bgrid.set(3, 4, Some(Pawn(Color.BLACK)))
      "have return false" in {
        Pawn(Color.BLACK).move(1, 4, 3, 4, bgrid) should be(false)
      }
      bgrid = bgrid.set(3, 4, None)
      bgrid = bgrid.set(2, 4, Some(Pawn(Color.WHITE)))
      "have return false too" in {
        Pawn(Color.BLACK).move(1, 4, 3, 4, bgrid) should be(false)
      }
    }

    "black can beat" should {
      bgrid = bgrid.set(3, 4, Some(Pawn(Color.BLACK)))
      bgrid = bgrid.set(4, 3, Some(Pawn(Color.WHITE)))
      bgrid = bgrid.set(4, 5, Some(Pawn(Color.BLACK)))
      "have return true" in {
        Pawn(Color.BLACK).move(3, 4, 4, 3, bgrid) should be(true)
      }
      "have return false" in {
        Pawn(Color.BLACK).move(3, 4, 4, 5, bgrid) should be(false)
      }
      "white cant beat on an empty cell" should {
        wgrid = wgrid.set(6, 0, Some(Pawn(Color.BLACK)))
        "have return false" in {
          Pawn(Color.BLACK).move(6, 0, 7, 1, wgrid) should be(false)
        }
      }
    }

    "all black moves" should {
      var bgrid: GridInterface = new Grid(8)
      bgrid = bgrid.set(3, 3, Some(Pawn(Color.BLACK)))
      bgrid = bgrid.set(4, 4, Some(Pawn(Color.WHITE)))
      bgrid = bgrid.set(4, 2, Some(Pawn(Color.BLACK)))
      "have return" in {
        Pawn(Color.BLACK).moveAll(3, 3, bgrid) should be(List((4,4), (4,3)))
      }
    }
    "all white moves" should {
      var wgrid: GridInterface = new Grid(8)
      wgrid = wgrid.set(3, 3, Some(Pawn(Color.WHITE)))
      wgrid = wgrid.set(2, 4, Some(Pawn(Color.WHITE)))
      wgrid = wgrid.set(2, 2, Some(Pawn(Color.BLACK)))
      "have return" in {
        Pawn(Color.WHITE).moveAll(3, 3, wgrid) should be(List((2,2), (2,3)))
      }
    }
    "all black moves and included double jump" should {
      var grid: GridInterface = new Grid(8)
      grid = grid.set(1, 3, Some(Pawn(Color.BLACK)))
      "have return" in {
        Pawn(Color.BLACK).moveAll(1, 3, grid) should be(List((2,3), (3,3)))
      }
    }
    "all white moves and included double jump" should {
      var grid: GridInterface = new Grid(8)
      grid = grid.set(6, 3, Some(Pawn(Color.WHITE)))
      "have return" in {
        Pawn(Color.WHITE).moveAll(6, 3, grid) should be(List((5,3), (4,3)))
      }
    }
    "all white moves blocked at start position" should {
      var grid: GridInterface = new Grid(8)
      grid = grid.set(6, 3, Some(Pawn(Color.WHITE)))
      grid = grid.set(5, 3, Some(Pawn(Color.BLACK)))
      grid = grid.set(4, 3, Some(Pawn(Color.WHITE)))
      "have return empty List" in {
        Pawn(Color.WHITE).moveAll(6, 3, grid) should be(List())
      }
    }
  }
}
