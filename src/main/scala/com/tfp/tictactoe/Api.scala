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
      val positionsOccupiedByX = allPositionsOf(X)(cells)
      val positionsOccupiedByO = allPositionsOf(O)(cells)

      allWinningCombinations.find(_ == positionsOccupiedByX)
        .map(_ => X)
        .orElse(allWinningCombinations.find(_ == positionsOccupiedByO)
        .map(_ => O))
    }

  def playerAt: HasBeenPlayed => Position => Option[Player] =
    board => position => board.cells find(_.position == position) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _)                  => None
    }

  def isDraw: HasFinished => Boolean =
    board => whoWon(board).isEmpty

  def allPositionsOf: Player => List[Cell] => List[Position] =
    player => cells => cells.filter(_.cellType == OccupiedBy(player)).map(_.position)

  def takeBack: HasBeenPlayed => IsPlayable = ???

}
