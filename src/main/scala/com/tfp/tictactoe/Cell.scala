package com.tfp.tictactoe

sealed trait CellType

case class OccupiedBy(player: Player) extends CellType
case object Empty extends CellType

case class Cell(cellType: CellType, position: Position)

case class Position(x: Int, y: Int)
