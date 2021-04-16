package com.tfp.tictactoe

import com.tfp.tictactoe.GameConfig.allWinningCombinations

object ApiCurried {

  def move: Player => Position => PlayableBoard => MoveResult =
    player => position => board => playerAt(board)(position)
                                     .map(_ => FailedMove)
                                     .getOrElse(successfulMoveFrom(board)(player)(position))

  def playerAt: Board => Position => Option[Player] =
    board => position => cellAtPosition(position)(board.cells) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _)                  => None
    }

  def whoWon: NonPlayableBoard => Option[Player] =
    board => winnerFrom(board.cells)

  def isDraw: NonPlayableBoard => Boolean =
    board => whoWon(board).isEmpty

  private def successfulMoveFrom: PlayableBoard => Player => Position => SuccessfulMove =
    playableBoard => player => position => {
      val resultingCells = transformCurrentCellsTo(cellWithPlayer)(playableBoard.cells)(position)(player)
      val resultingBoard =
        if (gameOverWith(resultingCells)) GameOverBoard(resultingCells)
        else if (!resultingCells.exists(_.cellType == Empty)) FinishedBoard(resultingCells)
        else InPlayBoard(resultingCells)
      SuccessfulMove(resultingBoard)
    }

  private def gameOverWith: List[Cell] => Boolean =
    cells => winnerFrom(cells).isDefined

  private def winnerFrom: List[Cell] => Option[Player] =
    cells => {
      allWinningCombinations.find(_ == positionsOf(X)(cells))
        .map(_ => X)
        .orElse(allWinningCombinations.find(_ == positionsOf(O)(cells))
        .map(_ => O))
    }

  private def positionsOf: Player => List[Cell] => List[Position] =
    player => cells => cells.filter(_.cellType == OccupiedBy(player)).map(_.position)

  private def cellWithPlayer: Cell => Position => Player => Cell =
    cell => position => player => cell match {
      case Cell(_, pos) if pos == position => Cell(OccupiedBy(player), position)
      case everyOtherCase                  => everyOtherCase
    }

  private def cellAtPosition: Position => List[Cell] => Option[Cell] =
    position => cells => cells find(_.position == position)

  private def transformCurrentCellsTo: (Cell => Position => Player => Cell) => List[Cell] => Position => Player => List[Cell] =
    f => cells => position => player => cells.map(f(_)(position)(player))

  def takeBack: PlayedBoard => PlayableBoard = ???

}
