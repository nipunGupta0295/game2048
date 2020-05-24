package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val sortedList = (permutation.min()?.rangeTo(permutation.size))?.toList()
    //  val sortedList = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    var permutations = 0
    val map = permutation.map { it to false }.toMutableList()
    var cycle = mutableListOf<Int>()
    for (i in map.indices) {
        if (!map[i].second && (sortedList?.get(i) != map[i].first)) {
            var cycleStart = map[i].first
            var cycleEnd = sortedList?.get(i)
            cycle.add(cycleStart)
            cycleEnd?.let { cycle.add(it) }
            while (cycleEnd != cycleStart) {
                val index = permutation.indexOf(cycleEnd)
                println(index)
                map[index] = Pair(cycleEnd, true) as Pair<Int, Boolean>
                val num = sortedList?.get(index)
                cycleEnd = num
                cycleEnd?.let { cycle.add(it) }
            }
            permutations += cycle.size - 2
            cycle = mutableListOf()
        }
    }
    val c = GameOfFifteen::class

    return permutations % 2 == 0


}