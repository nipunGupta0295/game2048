package board

import board.Direction.*
fun IntProgression.applyBounds(bound: Int): IntProgression =
        when {
            last > bound -> first..bound
            first > bound -> bound..last
            else -> this
        }

open class SquareBoardImp(final override val width: Int) : SquareBoard {
    private val cells = (1..width).flatMap { i ->
        (1..width).map { j ->
            Cell(i, j)
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return getAllCells().firstOrNull { it == Cell(i, j) }
    }

    override fun getCell(i: Int, j: Int): Cell =
            getCellOrNull(i, j) ?: throw IllegalArgumentException("Cell not found for i:$i, j:$j")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.applyBounds(width).map { j -> getCell(i, j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return (iRange).applyBounds(width).map { i -> getCell(i, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(this.i - 1, j)
            LEFT -> getCellOrNull(this.i, j - 1)
            DOWN -> getCellOrNull(this.i + 1, j)
            RIGHT -> getCellOrNull(this.i, j + 1)
        }
    }

}

class GameBoardImpl<T>(width: Int) : SquareBoardImp(width), GameBoard<T> {

    private val board: MutableMap<Cell, T?> = getAllCells()
            .map { it to null }
            .toMap<Cell, T?>()
            .toMutableMap()

    override operator fun get(cell: Cell): T? = board[cell]

    override operator fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
           board.entries.filter { predicate(it.value) }.map { it.key }

    override fun find(predicate: (T?) -> Boolean): Cell? =
            board.entries.find { predicate(it.value) }?.key

    override fun any(predicate: (T?) -> Boolean): Boolean =
            board.entries.any { predicate(it.value) }

    override fun all(predicate: (T?) -> Boolean): Boolean =
            board.entries.all { predicate(it.value) }
}
fun createSquareBoard(width: Int): SquareBoard = SquareBoardImp(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

