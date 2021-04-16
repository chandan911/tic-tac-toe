# tic-tac-toe

A simple project to demonstrate typed functional programming with Scala, focusing mainly on Algebraic Data Types (ADTs), Pattern Matching, and Higher Order Functions.

The goal is to write an API for the tic-tac-toe game. An API user, should be able to play a game of tic-tac-toe using this API, but importantly, it should be impossible for the API user to break the rules of the game. Specifically, if an attempt is made to break a rule, the API should reject the program. This is often done by way of a compile-time type error.

The following API functions should exist:

playerAt: takes a tic-tac-toe board and position and returns the (possible) player at a given position. This function works on any type of board.

move: takes a tic-tac-toe board and position and moves to that position (if not occupied) returning a new board. This function can only be called on a board that is empty or in-play. Calling move on a game board that is finished should not be possible.

whoWon: takes a tic-tac-toe board and returns the player that won the game (or none if neither has won). This function can only be called on a board that is finished. Calling whoWon on a game board that is empty or in-play should not be possible.

isDraw: checks if the game is a draw. Calling this on a game that isn't finisded should not be possible.

takeBack (last): takes either a finished board or a board in-play that has had at least one move and returns a board in-play. It is not allowed to call this function on an empty board.
