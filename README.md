# cs311_hw1
1. Joy Wood, Ellen Sartorelli
2. Node.java, EightsPlayer.java
3. No known bugs, but please open this file as markdown or else the table is very ugly. (link: https://github.com/ellensartorelli/cs311_hw1/blob/master/README.md)
4. Our hueristic utlizes the approach of "divide and conquer" as it prioritizes the proprer placement of the upper and left-most edges to then reduce the complexity of the of the remaining problem (when tiles 1, 2, 3, and 6 are in place, a 2x2 grid problem remains).

|Case   	|  Number of moves 	|   Number of nodes generated	|   	|   	|
|---	|---	|---	|---	|---	|
|   	|   	|   BFS	|  A*(Man) 	| A*(Our Heur.)  	|
|   2	|  2 	|   14	|   5	|   5	|
|   3	|  3 	|   27	|   9	|   9	|
|   4	|  5 	|   101	|   10	|   10	|
|   5	|  8 	|  361 	|   16	|  16 	|
|   6	|   10	|   1215	|  24 	|   19	|
|  Average for 500 iterations	|  NA 	|  1490682  	|  13063 	|  6438 	|


