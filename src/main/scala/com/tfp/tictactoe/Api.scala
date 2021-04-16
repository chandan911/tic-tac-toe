package com.tfp.tictactoe

import com.tfp.tictactoe.GameConfig.allWinningCombinations

object Api {

  def playerAt(board: Board, position: Position): Option[Player] =
    cellAtPosition(position, board.cells) flatMap {
      case Cell(OccupiedBy(player), _) => Some(player)
      case Cell(_, _) => None
    }

  def move(player: Player, position: Position, board: PlayableBoard): MoveResult =
    playerAt(board, position)
      .map(_ => FailedMove)
      .getOrElse(successfulMoveFrom(board, player, position))

  def whoWon(board: NonPlayableBoard): Option[Player] =
    winnerFrom(board.cells)

  def isDraw(board: NonPlayableBoard): Boolean = whoWon(board).isEmpty

  def takeBack(board: PlayedBoard): PlayableBoard = ???

  private def successfulMoveFrom(playableBoard: PlayableBoard, player: Player, position: Position): SuccessfulMove = {
    val resultingCells = transformCurrentCellsTo(cellWithPlayer, playableBoard.cells, position, player)
    val resultingBoard: PlayedBoard =
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

  private def cellAtPosition(position: Position, cells: List[Cell]): Option[Cell] = cells find (_.position == position)

  private def transformCurrentCellsTo(function: (Cell, Position, Player) => Cell,
                                      cells: List[Cell],
                                      position: Position,
                                      player: Player): List[Cell] = cells.map(function(_, position, player))

}
