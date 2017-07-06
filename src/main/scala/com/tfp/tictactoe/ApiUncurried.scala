package com.tfp.tictactoe

import com.tfp.tictactoe.TicTacToe.allWinningCombinations

object ApiUncurried {

  def move(player: Player, position: Position, board: IsPlayable): MoveResult =
    playerAt(board, position)
      .map(_ => FailedMove)
      .getOrElse(successfulMoveFrom(board, player, position))

  def playerAt(board: Board, position: Position): Option[Player] =
    cellAtPosition(position)(board.cells) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _) => None
    }

  def whoWon(board: HasFinished): Option[Player] =
    winnerFrom(board.cells)

  def isDraw(board: HasFinished): Boolean = whoWon(board).isEmpty

  def takeBack(board: HasBeenPlayed): IsPlayable = ???

  private def successfulMoveFrom(playableBoard: IsPlayable, player: Player, position: Position): SuccessfulMove = {
    val resultingCells = transformCurrentCellsTo(cellWithPlayer, playableBoard.cells, position, player)
    val resultingBoard =
      if (gameOverWith(resultingCells)) GameOverBoard(resultingCells)
      else if (!resultingCells.exists(_.cellType == Empty)) FinishedBoard(resultingCells)
      else InPlayBoard(resultingCells)
    SuccessfulMove(resultingBoard)
  }

  private def gameOverWith(cells: List[Cell]): Boolean = winnerFrom(cells).isDefined

  private def winnerFrom(cells: List[Cell]): Option[Player] = {
    allWinningCombinations.find(_ == positionsOf(X, cells))
      .map(_ => X)
      .orElse(allWinningCombinations.find(_ == positionsOf(O, cells))
        .map(_ => O))
  }

  private def positionsOf(player: Player, cells: List[Cell]): List[Position] =
    cells.filter(_.cellType == OccupiedBy(player)).map(_.position)

  private def cellWithPlayer(cell: Cell, position: Position, player: Player): Cell = cell match {
    case Cell(_, pos) if pos == position => Cell(OccupiedBy(player), position)
    case everyOtherCase => everyOtherCase
  }

  private def cellAtPosition: Position => List[Cell] => Option[Cell] =
    position => cells => cells find (_.position == position)

  private def transformCurrentCellsTo(f: (Cell, Position, Player) => Cell,
                                      cells: List[Cell],
                                      position: Position,
                                      player: Player): List[Cell] = cells.map(f(_, position, player))

}
