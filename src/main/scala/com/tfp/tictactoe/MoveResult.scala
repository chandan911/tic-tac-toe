package com.tfp.tictactoe

sealed trait MoveResult

case class SuccessfulMove(board: PlayedBoard) extends MoveResult
case object FailedMove extends MoveResult
