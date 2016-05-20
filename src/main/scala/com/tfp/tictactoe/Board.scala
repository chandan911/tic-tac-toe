package com.tfp.tictactoe

import com.tfp.tictactoe.TicTacToe.initialAllEmptyCells

sealed trait Board {
  def cells: List[Cell]
}
sealed trait IsPlayable extends Board
sealed trait HasBeenPlayed extends Board
sealed trait HasFinished extends HasBeenPlayed

case object UnPlayedBoard extends IsPlayable {
  val cells = initialAllEmptyCells
}
case class InPlayBoard(cells: List[Cell]) extends IsPlayable with HasBeenPlayed
case class GameOverBoard(cells: List[Cell]) extends HasFinished
case class FinishedBoard(cells: List[Cell]) extends HasFinished
