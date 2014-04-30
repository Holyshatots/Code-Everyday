/*
* Mitch McAffee - 4/11/14
* In this challenge, write a program that takes in three arguments, a start
*  temperature (in Celsius), an end temperature (in Celsius) and a step size. 
* Print out a table that goes from the start temperature to the end temperature, 
* in steps of the step size; you do not actually need to print the final end 
* temperature if the step size does not exactly match. You should perform input 
* validation: do not accept start temperatures less than a lower limit (which your
*  code should specify as a constant) or higher than an upper limit (which 
* your code should also specify). You should not allow a step size greater than
*  the difference in temperatures.
*/

#include <stdio.h>
#include <stdlib.h>

#define START_MIN -1000
#define END_MAX 1000

void ErrorHandler(int ErrCode){
	//Takes an error code and prints out the appropriate error message
switch (ErrCode) {
		case 1:
			puts("Usage: ./tempconverter StartTemp EndTemp StepSize");
			break;
		case 2:
			puts("StartTemp too low");
			break;
		case 3:
			puts("EndTemp too high");
			break;
		case 4:
			puts("StartTemp higher than EndTemp");
			break;
		case 5:
			puts("Step Size greater than difference in temperatures!");
			break;
		default:
			puts("Error!");
			break;
	}	
	}
int main(int argc, char *argv[]){
	long int StartTemp, EndTemp, StepSize;
	float currentC, currentF;
	int count = 0;
		
	if (argc != 4){
		ErrorHandler(1);
		return 1;
	}	
	
	StartTemp = atoi(argv[1]);
	EndTemp = atoi(argv[2]);
	StepSize = atoi(argv[3]);
	
	// Check bounds of input
	// Check if Start Temp is above min
	if (StartTemp < START_MIN){
		ErrorHandler(2);
		return 1;
	} 
	// Check if End Temp is lower than max
	if (EndTemp > END_MAX){
		ErrorHandler(3);
		return 1;
	}
	// Check if Start Temp is smaller than End temp
	if (StartTemp > EndTemp){
		ErrorHandler(4);
		return 1;
	}
	if ((StartTemp - EndTemp) > StepSize){
		ErrorHandler(5);
		return 1;
	}
	
	//Print out table
	printf("Celsius\tFahrenheit\n");
	printf("-------\t----------\n"); 
	do {	
		currentC = StepSize * count + StartTemp;
		currentF = currentC * 1.8 + 32;
		printf("%f %f\n", currentC, currentF);
		count++;
	} while(currentC < EndTemp);
	return 0;	
}
