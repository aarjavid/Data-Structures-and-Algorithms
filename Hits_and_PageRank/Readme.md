
System configuaration
System: MacOs
Java setup:-
java version "15.0.2" 2021-01-19
Java(TM) SE Runtime Environment (build 15.0.2+7-27)
Java HotSpot(TM) 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)

Tested on afsaccess1.njit.edu with Java Setup:-
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)

***Source files details:-
1)Source Code for Hits algorihm is in file : hits1996.java
2)Source Code for Page Rank algorihm is in file : pgrk1996.java

***Code Compilation:-
1) To compile Hits code, use command:- javac hits1996.java
2) To compile Page Rank code, use command:- javac pgrk1996.java

Above commands will generate hits1996.class and pgrk1996.class files repectively.
***All below information is comman for both Hits and Page Rank algorithm implementation,

***Running Code Files:-
1) To run Hits code, use command:- java hits1996 <Iterations> <IntialValue> <filename>
2) To run Page rank code, use command:- java pgrk1996 <Iterations> <IntialValue> <filename>

***All below information is comman for both Hits and Page Rank algorithm implementation,

*<iterations> = number of iterations the algorithm will be run for.
For iteration > 0 , the execution terminates after running num of #iterations. 
if iteration<=0, termination mode is through error bound.
if iteration=0, default error bound is 0.00001. 
if iteration < 0, error_bound is calculated by $10**iteration$
in error_bound termination mode, the execution terminates if abs(diff_error)<error_bound for every vertex of graph, where diff_error is
calculated by error = hub/authority/page_rnk of (this iteration - previous iteration).
PLEASE NOTE, in typewritten subject 8 notes, the terminating condition is mentioned as when diff_err <= error_bound, we terminate. BUT, in prp
document <p1610s21.pdf> it is quoted as, "If the difference is less than errorrate for EVERY VERTEX, then and only then can we stop at
iteration t.". Thus in this code, I have used terminating condition as diff_error < error_bound for every vertex, then stop.

*<InitialValue>  Value with every vertex of Graph is initialized in array H/A/P
acceptable values:- 0,1,-1,-2
for InitialValues : 0,1,-1,-2; arrays are initialized respectively with 0,1,1/N and 1/squareroot(N), where N = number of vertices.

*<filename> - .txt file which contains graph information with first line containing  NumOfVertices NumofEdges and then lines equal to
numofEdges , with each line containing two integers i and j, representing an edge from i to j.

****all above explanation about meaning and implementation of Iterations, initialvalue and filename is as per instructions of prp document
<p1610s21.pdf>., repeated here to confirm the implemenation approaches.

Example Commands to run files:-
$java hits1996 0 -1 samplegraph.txt
$java pgrk1996 5 1 samplegraph.txt

**** Both Hits and Page rank algorithms are implemented in SYNCHRONOUS MODE as asked in prp document p1610s21.pdf
**** In Hits, normalization is used, with normalization factor(scale factor) as described in Subject  8 notes, scaleing factor =
square_root(Sigma(a/h[i]**2)), where i belongs to every vertex of graph.
****In pageRank, parameter d = 0.85
****If N > 10, the code will not print Base iteration information and every single iteration information, but will just print values for A/H/P
for all the vertices once , after the code has converged(i.e. final iteration values). 
****If N > 10, default values of NumOfIterations set to 0 and initialvalue to -1.

