Aarjavi Dharaiya 

How to compile:-
g++ HashTableImplemenetation.cpp

How to run:-
./a.out testcase.txt

Ran successfully on gcc versions:-
personal system:- 4.2.1 and on afsconnect1.njit.edu with gcc version :-  4.8.5 20150623 (Red Hat 4.8.5-44) (GCC)

Assumptions made or constraints observed in code:-

1)the first two characters of command will be numeric code, 3rd character is space and key starts from 4rth character onwards
2)end of line of every command is either marked by \r, \n or \0
3)The code assumes there will not be a testcase that inserts duplicate keys without deleteion. Meaning Insert(alex), Delete(alex) , Insert(alex) is supported . But Insert(alex), Insert(alex) is not supported as code does not search for an existing key before inserting the new key.
It always inserts whatever key it gets as long as space is avaialable.
4)Empty() and Full() functions are implemented in the class Lexicon as they were described in the prp document , but are never called as no numeric code was mentioned for it
5) Max possible size of T is ideally UNIT_MAX , but will be limited by UNIT_MAX/15 as maximum possible size of A is UNIT_MAX bytes and relation between T and A is size of A = 15*size of T
6) when an overflow occurs, code calls overflow() with a resize factor. Intial value of resize factor is 2. The overflow() is called in a loop continuosly until we can successfully insert the new key or if maximum size limit of A is reached, whichever event happens first. resize
factor is doubled in every call of overflow() in the loop. If maximum size limit of A is reached, we report "key cannot be inserted".
7)Size of table T (N) and array A, gets doubled after every resize (overflow()).
8)Relation between size of T and A is set fixed to , size of A = 15*N chars, N = slots of T.
9)Code is tested to be working fine in batch() with input files of commands of type .txt or .bat extensions.

**There are no bugs in code
