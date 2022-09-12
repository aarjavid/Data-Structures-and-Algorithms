//Aarjavi Dharaiya 1996
#include <iostream>
#include <string.h>
#include <stdlib.h>
#include <fstream>
#include <climits>
using namespace std;


//overflow cases:-
//1-T is full
//2-A is full or dirty
//3-A not full but insufficient to save entire (new word + 1 null character)

//underflow cases:-
//1-empty table T
//2-key not existing

class Lexicon_1996 {
	
	unsigned int *T;
	char *A;
	unsigned int as;
	unsigned int N = 0;
	unsigned int n = 0;
	unsigned int A_index = 0; 
	
	const unsigned int EMPTY = UINT_MAX;
	const unsigned int DELETED = UINT_MAX - 1;	

	public:
	//Create, Print, Empty, Full, Batch, Insert, Delete, Search.
	void Print() {
		cout << "\n   T\t\t" ;
		cout << "A: ";
		for(unsigned int i=0;i<A_index;i++) {
			if(A[i]=='\0')
				cout << '\\';
			else cout << A[i];
		}
		cout << endl;
		for(unsigned int i=0;i<N;i++) {
			if(T[i] >= DELETED) {
				cout << i << ":" << endl;
			}
			else
				cout << i << ": " << T[i] << endl;
		}
		
		cout << "\n" << endl;

	}

	void Create(unsigned int table_size) {
	

		N = table_size;
		T = new unsigned int[N];
		as = 15 * table_size; 
		A = new char[as];
		for(unsigned int i = 0; i< N; i++)  
			T[i] = EMPTY;
		for(unsigned int i = 0; i< 15*N; i++)  
			A[i] = ' ';
	}

	unsigned int HashDK(char * X) {
		int ascii_sum = 0;
		for(int i = 0; i<strlen(X); i++) {
			ascii_sum += X[i];
		}
		unsigned int hd_k = (ascii_sum - 2 )% N;
		return hd_k;
	}
	bool overflow(int resize_factor) {

		unsigned int oldN = N;
		N = resize_factor*N;
		unsigned int old_as = as;
		as = resize_factor* as;

		unsigned int* oldTable = T;
		char * oldA = A;
		T = new  unsigned int[N];
		A = new char[as];
		unsigned int A_index_old = A_index;
	
		A_index = 0;
		bool insert_status = false;
		unsigned int h_k;
		for(unsigned int i = 0; i< N; i++)  
			T[i] = EMPTY;
		for(unsigned int i = 0; i< 15*N; i++)  
			A[i] = ' ';
		for(unsigned int oi = 0; oi < oldN; oi++) {
			if(oldTable[oi] < DELETED) {
				
				insert_status = false;
				unsigned int i =0 ;
				while(i<N and insert_status != true){
					h_k = (HashDK(&oldA[oldTable[oi]]) + i*i) % N ;
					if(T[h_k] >= DELETED) {
						insert_status = true;		
					}
				i++;
				}
				
				if(insert_status == false) {
					//cout << "\n\n\n**************doubling size not sufficient for this overflow*****\n\n\n";
					delete []T;
					delete []A;
					T = oldTable;
					A = oldA;
					N = oldN;
					as = old_as;
					A_index = A_index_old;
					return false; //overflow() with N;
				}
			
				T[h_k] = A_index;	
				for(unsigned int j =oldTable[oi]; j < oldTable[oi]+strlen(&oldA[oldTable[oi]]); j++) {
					A[A_index] = oldA[j];
					A_index++;
				}
				A[A_index] = '\0';
				A_index++;
			}


		}
		delete[] oldTable;
		delete[] oldA;
		return true;
	}

	bool Insert(char* X) {

		unsigned int h_k = 0;
		unsigned int i = 0;
		bool insert_status = false;
		
		if(as - A_index <= strlen(X)) {
			//cout << "as " << as << " a_index : " <<  A_index << " strlen_x "<< strlen(X) << endl;
			//cout << "overflow() with A";
			return false; // overflow() with A;
		} 

		if(n==N) {
			//cout << n << " " << N << endl;
			//cout << "overflow() with N-1" << endl;;
			return false; // overflow with N;
		}

		while(i<N and insert_status != true){
			h_k = (HashDK(X) + i*i) % N ;
			if(T[h_k] >= DELETED) {
				insert_status = true;		
			}
			i++;
		}
		if(insert_status == false) {
			//cout << "overflow() with N-2" << endl;
			return false; //overflow() with N;
		}
			
		T[h_k] = A_index;	
		n++;	
		//cout << "A_index is " << A_index  << endl;
		for(unsigned int j =0; j < strlen(X); j++) {
			A[A_index] = X[j];
			A_index++;
		}
		A[A_index] = '\0';
		A_index++;
		
		/*cout << "A_index is " << A_index << "strlen(A) is " << strlen(A) << endl;*/
		return true;

	}

	unsigned int Delete(char * X) {

		unsigned int h_k = 0;
		unsigned int i = 0;
		bool delete_status = false;
			
		if(N < 1)
			return EMPTY; //underflow - table not existing, intially
		
		if(n==0)
			return EMPTY; //underflow - table empty

		do{
			h_k = (HashDK(X) + i*i) % N ;
			if(T[h_k] < DELETED) {
				if(!strcmp(&A[T[h_k]],X)) {			
					delete_status = true;	
					}
			}
			i++;
		}while(i<N and delete_status != true and T[h_k] != EMPTY );

		if(delete_status == false) 
			return DELETED; //underflow wih key not existing

		for(unsigned int j = T[h_k]; j < T[h_k] + strlen(X); j++) {
			A[j] = '*';
		}		
		T[h_k] = DELETED;	
		n--;	
		return h_k;

	}

	unsigned int Search(char * X) {

		unsigned int h_k = 0;
		unsigned int i = 0;
		
			
		if(N < 1)
			return EMPTY; // table not existing, intially
		
		if(n==0)
			return EMPTY; //table empty

		do{
			h_k = (HashDK(X) + i*i) % N ;
			if(T[h_k] < DELETED) {
				//cout << "given key is " << X << endl;
				//cout << "  str is : " << &A[T[h_k]]  << endl;
				//cout << "hk is " << h_k << " T[hk]  is " << T[h_k] << "  A[Thk] is " << A[T[h_k]] << endl;
				if(!strcmp(&A[T[h_k]],X)) {			
					//cout << "Matched " <<endl;
					return h_k;
					}
				}
			i++;		
			}while(i<N and T[h_k] != EMPTY);
		
			
		return EMPTY; //key not existing
	}

	void batch(char* filename) {
		ifstream cmnd_file (filename);	
		if (cmnd_file.is_open()){
			string line;
			while ( getline (cmnd_file,line) ){
			//	cout <<"\nNEW LINE ***" << endl;
			//	cout << line << '\n';
				int i =0;
				char cmnd[2];
				char key[47];
				cmnd[0] = line[0];
				cmnd[1] = line[1];
			//	cout << cmnd  << endl;
				int p1 = 3;
				while(line[p1] != '\r' and line[p1] != '\n' and line[p1] != '\0'){
					key[p1-3] = line[p1];
					p1++;
				}
				key[p1-3] = '\0';
			//	cout << key << "\n" << endl;
				int cmnd_i = atoi(cmnd);
			//	cout << cmnd_i <<endl;
				
				switch (cmnd_i) {
					case 10: {
						//Insert

						bool insert_status = Insert(key);
						if(insert_status)
							 {//cout << "insert_status succedded" << insert_status << endl;
							 }
						else	{
							int resize_factor = 1;
							
							do {
								resize_factor = resize_factor * 2;
								bool overflow_status = overflow(resize_factor);
								if(overflow_status) {
									insert_status = Insert(key);
								}
							}while(insert_status != true and as*2 < UINT_MAX); // #FIXME
							if(insert_status) {
								//cout << "insert_status succedded" << insert_status << endl;
							}
							else	
								cout << "Key not inserted - Array A reached maximum possible size limit = UNIT_MAX" << endl;
							
						}
						
						//Print();
						break; 
					}
					case 11: {
						//delete
						unsigned int delete_slot = Delete(key);
						if (delete_slot < DELETED) {
							std::cout.width(15); std::cout << std::left << key << "    deleted from slot " << delete_slot << endl;
						}
						else {
							std::cout.width(15); std::cout << std::left << key << "    not found in table - delete not possible " << endl;
						}
						//Print();
						break;
					}
					case 12:{
						unsigned int found_slot = Search(key);
						if (found_slot < DELETED) {
							std::cout.width(15); std::cout << std::left << key << "    found at slot " << found_slot << endl;
						}
						else {
							std::cout.width(15); std::cout << std::left << key << "    not found " << endl;
						}
						//search
						//Print();
						break;
					}
					case 13:{
						//print
						Print();
						break;
					}
					case 14:{
						//create
						unsigned int table_size = atoi(key);
						Create(table_size);
						break;
					}
					case 15: {
						//comment
						break;
					}
					default : {
						cout << "Enter valid command " << endl;
						break;
					}
				}
				

				
			}
			cmnd_file.close();

		} 
		else { cout << "Could not open file";} 
	}

	bool Empty() {
		if(n==0)  //Just checking for Empty Table not for empty array which makes more sense for the functionality 
			return true;
		else
			return false;
	}

	bool Full() {
		if(n==N or A_index == as) 	
			return true;
		else
			return false;
	}

	
};

int main(int argc, char * argv[]) {
	if(argc==1) {
		cout << "No input provided\n";
		return 0;
	}
	Lexicon_1996 L;
	L.batch(argv[1]);

	return 0;
}
