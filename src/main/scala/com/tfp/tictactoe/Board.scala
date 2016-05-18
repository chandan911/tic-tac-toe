package com.tfp.tictactoe

sealed trait Board
sealed trait IsPlayable extends Board
sealed trait HasBeenPlayed extends Board {
  def cells: List[Cell]
}
sealed trait HasFinished extends HasBeenPlayed

case object UnPlayedBoard extends IsPlayable
case class InPlayBoard(cells: List[Cell]) extends IsPlayable with HasBeenPlayed
case class GameOverBoard(cells: List[Cell]) extends HasFinished
case class FinishedBoard(cells: List[Cell]) extends HasFinished
