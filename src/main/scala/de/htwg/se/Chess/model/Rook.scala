package de.htwg.se.Chess.model

case class Rook(c: Color.Value) extends Figure {

  override val color: Color.Value = c

  override val typ: FigureType.Value = FigureType.ROOK

  override def move(oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, grid: Grid): Boolean = {
    val revColor = colorReverse(color)
    val moves = (-1, 0) :: (1, 0) :: (0, -1) :: (0, 1) :: Nil

    for (i <- moves) {
      for (j <- 1 to 8) {
        val move: (Int, Int) = (oldRow + i._1 * j, oldCol + i._2 * j)
        if (move._1 < 8 && move._2 < 8 && move._1 >= 0 && move._2 >= 0) {
          if (move == (newRow, newCol)) {
            if (!wayIsBlocked((oldRow, oldCol), (newRow, newCol), i, grid)) {
              if (grid.cell(move._1, move._2).iSet) {
                grid.cell(move._1, move._2).value match {
                  case Some(res) => res.Color match {
                    case `color` => return false
                    case `revColor` => return true
                  }
                  case None => return true
                }
              } else return true
            }
          }
        }
      }
      false
    }
  }

  def wayIsBlocked(oldPlace: (Int, Int), newPlace: (Int, Int), direction: (Int, Int), grid: Grid): Unit = {
    val len: Int = oldPlace._1 - newPlace._1
    for(i <- 1 to len) {
      val move: (Int, Int) = (oldPlace._1 + direction._1 * i, oldPlace._2 + direction._2 * i)
      if (move == newPlace) return false
      if (grid.cell(move._1, move._2).iSet) return true
    }
    false
  }

    def colorReverse(color: Color.Value): Color.Value = color match {
      case Color.WHITE => Color.BLACK
      case Color.BLACK => Color.WHITE
    }

    override def getType(){
      FigureType.Value = typ
    }

    override def toString: String = {
      color match {
        case Color.BLACK => "♜"
        case Color.WHITE => "♖"
      }
    }
  }
