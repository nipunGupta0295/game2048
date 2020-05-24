package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private lateinit var permutation: List<Int>
    private val board = createGameBoard<Int>(4)
    override fun initialize() {
        var k = 0
        permutation = initializer.initialPermutation
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                if(k !=permutation.size) {
                    board[Cell(i, j)] = permutation[k++]
                }
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var sequence = 1
        return board.getAllCells().all { board[it] == null || board[it] == sequence++ }
    }

    override fun processMove(direction: Direction) {
        /*val nullCell = board.find { it == null }
        if (direction == Direction.UP) {
            if (nullCell != null) return
        }*/
        with(board) {
            find { it == null }?.also {
                it.getNeighbour(direction.reversed())?.also { neighbour ->
                    this[it] = this[neighbour]
                    this[neighbour] = null
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board[Cell(i, j)]
    }
}



