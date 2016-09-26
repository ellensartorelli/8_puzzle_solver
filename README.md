# cs311_hw1
1. Joy Wood, Ellen Sartorelli
2. Node.java, EightsPlayer.java
3. No known bugs
4. Our hueristic utlizes the approach of "divide and conquer" as it prioritizes the proprer placement of the upper and left-most edges to then reduce the complexity of the of the remaining problem (when tiles 1, 2, 3, and 6 are in place, a 2x2 grid problem remains).

|Case   	|  Number of moves 	|   Number of nodes generated	|   	|   	|
|---	|---	|---	|---	|---	|
|   	|   	|   BFS	|  A*(Man) 	| A*(Our Heur.)  	|
| 1  	|   	|   	|   	|   	|
|   2	|   	|   	|   	|   	|
|   3	|   	|   	|   	|   	|
|   4	|   	|   	|   	|   	|
|   5	|   	|   	|   	|   	|
|   6	|   	|   	|   	|   	|
|   7	|   	|   	|   	|   	|
|  Average (BFS: 100, A\*s: 500)	|  NA 	|    	|  13063 	|  6438 	|


