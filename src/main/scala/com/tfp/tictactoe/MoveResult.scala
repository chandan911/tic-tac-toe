package com.tfp.tictactoe

sealed trait MoveResult

case class SuccessfulMove(board: Board) extends MoveResult
case object FailedMove extends MoveResult
