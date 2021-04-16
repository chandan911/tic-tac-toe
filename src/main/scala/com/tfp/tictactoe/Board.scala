package com.tfp.tictactoe

import com.tfp.tictactoe.GameConfig.initialAllEmptyCells

sealed trait Board {
  def cells: List[Cell]
}
sealed trait PlayableBoard extends Board
sealed trait PlayedBoard extends Board
sealed trait NonPlayableBoard extends PlayedBoard

case object UnPlayedBoard extends PlayableBoard {
  val cells: List[Cell] = initialAllEmptyCells
}
case class InPlayBoard(cells: List[Cell]) extends PlayableBoard with PlayedBoard
case class GameOverBoard(cells: List[Cell]) extends NonPlayableBoard
case class FinishedBoard(cells: List[Cell]) extends NonPlayableBoard
