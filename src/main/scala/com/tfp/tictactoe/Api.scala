package com.tfp.tictactoe

import com.tfp.tictactoe.TicTacToe.{allWinningCombinations, initialAllEmptyCells}

object Api {

  def move: Player => Position => IsPlayable => MoveResult = player => position => {
    case UnPlayedBoard =>
      val resultingCells = getNewCellsFromCurrent(initialAllEmptyCells)(position)(player)(cellWithPlayer)
      SuccessfulMove(InPlayBoard(resultingCells))

    case inPlayBoard: InPlayBoard =>
      inPlayBoard.cells.find(_.position == position).map(_.cellType) match {
        case Some(OccupiedBy(_)) => FailedMove
        case _                   =>
          val resultingCells = getNewCellsFromCurrent(inPlayBoard.cells)(position)(player)(cellWithPlayer)
          val resultingBoard =
            if (gameOverWith(resultingCells)) GameOverBoard(resultingCells)
            else if (!resultingCells.exists(_.cellType == Empty)) FinishedBoard(resultingCells)
            else InPlayBoard(resultingCells)
          SuccessfulMove(resultingBoard)
      }
  }

  def gameOverWith: List[Cell] => Boolean =
    cells => winnerFrom(cells).isDefined

  def whoWon: HasFinished => Option[Player] =
    board => winnerFrom(board.cells)

  def winnerFrom: List[Cell] => Option[Player] =
    cells => {
      allWinningCombinations.find(_ == positionsOf(X)(cells))
        .map(_ => X)
        .orElse(allWinningCombinations.find(_ == positionsOf(O)(cells))
        .map(_ => O))
    }

  def playerAt: HasBeenPlayed => Position => Option[Player] =
    board => position => board.cells find(_.position == position) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _)                  => None
    }

  def isDraw: HasFinished => Boolean =
    board => whoWon(board).isEmpty

  def positionsOf: Player => List[Cell] => List[Position] =
    player => cells => cells.filter(_.cellType == OccupiedBy(player)).map(_.position)

  def cellWithPlayer: Cell => Position => Player => Cell =
    cell => position => player => cell match {
      case Cell(_, pos) if pos == position => Cell(OccupiedBy(player), position)
      case everyOtherCase                  => everyOtherCase
    }

  def getNewCellsFromCurrent: List[Cell] => Position => Player => (Cell => Position => Player => Cell) => List[Cell] =
    cells => position => player => f => cells.map(f(_)(position)(player))

  def takeBack: HasBeenPlayed => IsPlayable = ???

}
