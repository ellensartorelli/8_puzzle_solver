# cs311_hw1
1. Joy Wood, Ellen Sartorelli
2. Node.java, EightsPlayer.java
3. No known bugs, but please open this file as markdown or else the table is very ugly. (link: https://github.com/ellensartorelli/cs311_hw1/blob/master/README.md)

Our hueristic utilizes the approach of "divide and conquer" as it prioritizes the proper placement of the upper and left-most edges to  reduce the complexity of the remaining problem. When tiles 1, 2, 3, and 6 are in place, a 2x2 grid problem remains. A solvable 2x2 puzzle can be solved in a max of 4 moves, meaning very few extra nodes will be generated. The following board configuration represents an example of a "worst case scenario" after using our heuristic to arrange the upper and left column. 

|   1|   2|   3|
|---|---|---|
|   5|   0|   6|
|   4|   7|   8|

We tested our heuristic against A* on one of the most difficult 8 puzzles, and the results were impressive. It's good at minimizing space complexity for hard puzzles, but bad at the end game, and easy puzzles. 

|   6|   4|   7|
|---|---|---|
|   8|   5|   0|
|  3|   2|  1|

Our heuristic:
Number of nodes generated to solve: 758<br />
Number of moves to solve: 31<br />
Number of solutions so far: 1<br />
<br />
A*:
Number of nodes generated to solve: 13843<br />
Number of moves to solve: 31<br />
Number of solutions so far: 1<br />

|Case   	|  Number of moves 	|   Number of nodes generated	|   	|   	|
|---	|---	|---	|---	|---	|
|   	|   	|   BFS	|  A*(Man) 	| A*(Our Heur.)  	|
|   2	|  2 	|   14	|   5	|   5	|
|   3	|  3 	|   27	|   9	|   9	|
|   4	|  5 	|   101	|   10	|   10	|
|   5	|  8 	|  361 	|   16	|  16 	|
|   6	|   10	|   1215	|  24 	|   19	|
|  Average for 500 iterations	|  NA 	|  1490682  	|  13063 	|  6438 	|



 * All group members were present and contributing during all work on this project.
 * I have neither given nor recieved unauthorized aid on this assignment.

