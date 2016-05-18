package com.tfp.tictactoe

import com.tfp.tictactoe.TicTacToe.{allWinningCombinations, initialAllEmptyCells}

object Api {

  def move: Player => Position => IsPlayable => MoveResult = player => position => {
    case UnPlayedBoard =>
      val resultingCells = initialAllEmptyCells map {
        case Cell(_, pos) if pos == position => Cell(OccupiedBy(player), position)
        case x                               => x
      }
      SuccessfulMove(InPlayBoard(resultingCells))

    case inPlayBoard: InPlayBoard =>
      inPlayBoard.cells.find(_.position == position) match {
        case Some(Cell(OccupiedBy(_), _)) => FailedMove
        case _                            =>
          val resultingCells = inPlayBoard.cells map {
            case Cell(_, pos) if pos == position => Cell(OccupiedBy(player), position)
            case x                               => x
          }
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
      val cellsOccupiedByX = cells.filter(_.cellType == OccupiedBy(X)).map(_.position)
      val cellsOccupiedByO = cells.filter(_.cellType == OccupiedBy(O)).map(_.position)
      if (allWinningCombinations contains cellsOccupiedByX) Some(X)
      else if (allWinningCombinations contains cellsOccupiedByO) Some(O)
      else None
    }

  def playerAt: HasBeenPlayed => Position => Option[Player] =
    board => position => board.cells find(_.position == position) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _)                  => None
    }

  def takeBack: HasBeenPlayed => IsPlayable = ???

  def isDraw: HasFinished => Boolean =
    board => whoWon(board).isEmpty

}
