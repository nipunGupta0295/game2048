package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val pair = initializer.nextValue(this)
    if (pair != null) {
        this[pair.first] = pair.second
    }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    /* val list = rowOrColumn.map { this[it] }.toMutableList()
     val newList = list.moveAndMergeEqual { a -> a * 2 }
     val bool = list == newList
     if (bool) {
         rowOrColumn.forEach { this[it] = null }
         for (i in newList.indices) {
             this[rowOrColumn[i]] = newList[i]
         }
     }
     return bool*/
    val movedAndMerged = rowOrColumn
            .map { cell -> this[cell] }
            .moveAndMergeEqual { value -> value * 2 }

    val moved = movedAndMerged.isNotEmpty() && (movedAndMerged.size != rowOrColumn.size)
    if (moved) {
        rowOrColumn.forEach { cell -> this[cell] = null }
        movedAndMerged.forEachIndexed { index, value ->
            val cell = rowOrColumn[index]
            this[cell] = value
        }
    }
    return moved
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    when (direction) {
        Direction.UP -> {
            var flag = false
            for (i in 1..width) {
                val col = this.getColumn(1..this.width, i)
                val bool = moveValuesInRowOrColumn(col)
                if (bool) flag = true
            }
            return flag
        }
        Direction.DOWN -> {
            var flag = false
            for (i in 1..width) {
                val col = this.getColumn(this.width downTo 1, i)
                val bool = moveValuesInRowOrColumn(col)
                if (bool) flag = true

            }
            return flag
        }
        Direction.LEFT -> {
            var flag = false
            for (i in 1..width) {
                val row = this.getRow(i, 1..this.width)
                val bool = moveValuesInRowOrColumn(row)
                if (bool) flag = true
            }
            return flag
        }
        else -> {
            var flag = false
            for (i in 1..width) {
                val row = this.getRow(i, this.width downTo 1)
                val bool = moveValuesInRowOrColumn(row)
                if (bool) flag = true
            }
            return flag
        }
    }
}

