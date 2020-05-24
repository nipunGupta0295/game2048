package games.gameOfFifteen

import kotlin.random.Random
import kotlin.random.nextInt

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val list = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).toMutableList()
        val newList=list.shuffled().toMutableList()
        while (!isEven(newList)) {
            val i = Random.nextInt(0..14)
            val j = Random.nextInt(0..14)
            val temp = newList[i]
            newList[i] = newList[j]
            newList[j] = temp
        }
        newList.toList()
    }
}

