/* Mitch McAffee 
 * HSCTF - Great Wall of North
 * ----------------
 * High School North recently built a wall around campus to prevent Pirates from entering the building, but they need
to ensure that nobody will climb over it. Therefore, the administration has decided to hire guards to monitor the
outside of the wall. 

There are N applicants for the position of guard, but only a few can be accepted. Each guard j can only monitor the range
in between integers s[j] and f[j] inclusive (you can think of the wall as a number line from 0 to an integer K). Additionally, it's proper etiquette to not monitor a region another guard is monitoring. In other words, no two guards can overlap regions (some of the applicants will overlap in range though).
Each guard j must also be paid p[j] dollars.

You are given a text file where each row has three space-separated integers that correspond to a guard. The first integer
is the s[i], the second is f[i], and the third is p[i].

s[j] = First (Begin range)
f [j] = Second (End of range)
p [j] = Third (Price of guard)
N = 51500;  (number of applicants)
K = 31078657 (Size of wall)

Your goal is to find the minimum amount of money the school has to pay to have every coordinate between 0 and K (inclusive)
monitored.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUMBER_OF_APP 51500	// N
#define SIZE_OF_WALL 31078657	// K
#define MAXLEN 1000 // Max length of each line

// Comparator functions for qsort
// Will sort so the highest numbers are first
	struct guard {
		int bgnrange; // s[j]
		int endrange; // f[j]
		int price; // p[j]
		float wage; // Price per unit
	};
	
int compare_guards(const void* a, const void* b) {
	return ( *(guard*)b->wage - *(guard*)a->wage );
}

int main(int argc, char *argv[]){
	char *line;
	int i;
	char *p;
	int size;
	int wall = SIZE_OF_WALL;
	int totalcost = 0;
	struct guard guardList;
	// Take file input
	if(argc != 2) {
		puts("Usage: ./size filename.txt");
		return 0;
	}
	FILE *in_file = fopen(argv[1], "r");
	if(in_file == NULL){
		printf("Could not open %s.\n", argv[1]);
		return 1;
	}
	
	// METHOD 1
	// Find the most cost efficient guard
	// Use the max range for that guard
	// Do this again for the next guards
	// Find the guard with the required amount of
	
	// METHOD 2
	// Make list of most cost-effective guards
	// -- Divide max range by the cost
	// Go down the list subtracting the max range from the total wall leng
	// Once it is 0 or below zero, add the total price for the guards
	// PROFIT
	
	fgets(line, MAXLEN, in_file);
	size = atoi(line);
	
	guardList = (struct guard*)(malloc(size* sizeof(struct guard)));
	
	// Fill the structs for all guards using strtok
	for(i=0;i<size;i++){
		fgets(line, MAXLEN, in_file);
		
		strtok(line, " ");
		guardList[i].bgnrange = atoi(p);
		
		p = (char*)strtok(NULL, " ");
		guardList[i].endrange = atoi(p);
		
		p = (char*)strtok(NULL, " ");
		guardList[i].price = atoi(p);
		
		guardList[i].wage = (guardList[i].endrange-guardList[i].bgnrange)/guardList[i].price;
		
	}
	// Sort by the most cost effective
	qsort(guardList, i, sizeof(guard), compare_guards);
	// Find needed number of guards
	i = 0;
	while(wall>0){
		wall -= guardList[i].wage;
		totalcost += guardList[i].wage;
		i++;
	}
	printf("The total cost is: %d", totalcost);
	// Clean up
	free(guardList);
	fclose(in_file);
	return 0;
}

/* Reading in file notes
Thus, continue making this call until there are no more tokens in the string tokenizer. Either the number of tokens in the string will be known, OR you can check to see if p is NULL or not, when it is NULL, there are no more tokens left in the string tokenizer and it has to be "reseeded" with a new string to tokenize. Here is an example of reading in department information separated by tabs:

fgets(line, MAXLEN, fin);
size = atoi(line);
deptList = (struct dept*)(malloc(size*sizeof(struct dept)));
    
for (i=0; i<size; i++) {
        fgets(line, MAXLEN, fin);
        strtok(line, "\t");
        strcpy(deptList[i].name, line);

        p = (char*)strtok(NULL, "\t");
        deptList[i].numFaculty = atoi(p);

        p = (char*)strtok(NULL, "\t");
        deptList[i].totalSalary = atoi(p);  
        p = (char*)strtok(NULL, "\t"); // Unnecessary 
    }
*/
