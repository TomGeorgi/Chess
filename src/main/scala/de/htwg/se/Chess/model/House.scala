package de.htwg.se.Chess.model

case class House(private val cells:Vector[Cell])  {
  def cell(index: Int): Cell = cells(index)
}
