package de.htwg.se.Chess.model.gridComponent.gridBaseImpl

import de.htwg.se.Chess.model.figureComponent.Color
import de.htwg.se.Chess.model.figureComponent.figureBaseImpl._
import de.htwg.se.Chess.model.gridComponent.{CellInterface, GridInterface}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {

  "A Grid is the playingfield of Chess. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are only in size 8" in {
        val grid = new Grid(8)
      }
      "for test purposes only created with a Matrix of Cells" in {
        val grid = Grid(new Matrix(8, Cell(None)))
        val testgrid = Grid(Matrix[CellInterface](Vector(Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)),
          Vector(Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None), Cell(None)))))
      }
      "created properly but empty" should {
        val grid = new Grid(8)
        "give access to its Cells" in {
          grid.cell(0, 0) should be(Cell(None))
        }
        "allow to set individual Cells and remain immutable" in {
          val changeGrid = grid.set(0, 0, Some(Pawn(Color.BLACK)))
          changeGrid.cell(0, 0) should be(Cell(Some(Pawn(Color.BLACK))))
          grid.cell(0, 0) should be(Cell(None))
        }
      }
      "prefilled with Figures" should {
        val grid = new Grid(8).fill()
        "have the right Figures in the right Cells" in {
          grid.cell(0, 0) should be(Cell(Some(Rook(Color.BLACK))))
          grid.cell(0, 1) should be(Cell(Some(Knight(Color.BLACK))))
          grid.cell(0, 2) should be(Cell(Some(Bishop(Color.BLACK))))
          grid.cell(0, 3) should be(Cell(Some(Queen(Color.BLACK))))
          grid.cell(0, 4) should be(Cell(Some(King(Color.BLACK))))
          grid.cell(0, 5) should be(Cell(Some(Bishop(Color.BLACK))))
          grid.cell(0, 6) should be(Cell(Some(Knight(Color.BLACK))))
          grid.cell(0, 7) should be(Cell(Some(Rook(Color.BLACK))))
          grid.cell(7, 0) should be (Cell(Some(Rook(Color.WHITE))))
          grid.cell(7, 1) should be(Cell(Some(Knight(Color.WHITE))))
          grid.cell(7, 2) should be(Cell(Some(Bishop(Color.WHITE))))
          grid.cell(7, 3) should be(Cell(Some(Queen(Color.WHITE))))
          grid.cell(7, 4) should be(Cell(Some(King(Color.WHITE))))
          grid.cell(7, 5) should be(Cell(Some(Bishop(Color.WHITE))))
          grid.cell(7, 6) should be(Cell(Some(Knight(Color.WHITE))))
          grid.cell(7, 7) should be(Cell(Some(Rook(Color.WHITE))))
          grid.cell(7, 7) should be (Cell(Some(Rook(Color.WHITE))))

          for {
            col <- 0 until 8
          } grid.cell(1, col) should be (Cell(Some(Pawn(Color.BLACK))))

          for {
            col <- 0 until 8
          } grid.cell(6, col) should be (Cell(Some(Pawn(Color.WHITE))))
        }
      }
      "is check" should {
        var grid: GridInterface = new Grid(8)
        "when the king is under attack of an enemy figure" in {
          grid = grid.set(0, 0, Some(Bishop(Color.BLACK)))
          grid = grid.set(2, 1, Some(Queen(Color.WHITE)))
          grid = grid.set(2, 2, Some(King(Color.WHITE)))
          grid = grid.set(2, 3, Some(Pawn(Color.BLACK)))
          grid.isInCheck(Color.WHITE) should be(true)
        }
      }
      "is checkmate" should {
        var grid: GridInterface = new Grid(8)
        "when the is under attack and he cant be saved with the next move" in {
          grid = grid.set(0 ,0, Some(King(Color.BLACK)))
          grid = grid.set(2, 0, Some(Rook(Color.WHITE)))
          grid = grid.set(0, 2, Some(Rook(Color.WHITE)))
          grid = grid.set(2, 2, Some(Queen(Color.WHITE)))
          grid.isCheckMate(Color.BLACK)
        }
      }
    }
  }
}
