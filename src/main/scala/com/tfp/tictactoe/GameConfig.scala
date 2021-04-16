package com.tfp.tictactoe

object GameConfig {

  val initialAllEmptyCells: List[Cell] = {
    for {
      x <- 0 to 2
      y <- 0 to 2
    } yield Cell(Empty, Position(x, y))
  }.toList

  val allWinningCombinations: List[List[Position]] = List(
    List(Position(0,0), Position(1,0), Position(2,0)),
    List(Position(0,1), Position(1,1), Position(2,1)),
    List(Position(0,2), Position(1,2), Position(2,2)),
    List(Position(0,0), Position(0,1), Position(0,2)),
    List(Position(1,0), Position(1,1), Position(1,2)),
    List(Position(2,0), Position(2,1), Position(2,2)),
    List(Position(0,0), Position(1,1), Position(2,2)),
    List(Position(0,2), Position(1,1), Position(2,0))
  )

}
