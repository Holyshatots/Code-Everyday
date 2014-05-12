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

void getInputs(int *StartTemp, int *EndTemp, int *StepSize){
// If nothing is specified, allow inputs to be passed in during execution
	printf("Enter starting temperature:");
	scanf("%d", StartTemp);
	printf("Enter ending temperature:");
	scanf("%d", EndTemp);
	printf("Enter step size:");
	scanf("%d", StepSize);
	return;
}

int main(int argc, char *argv[]){
	int StartTemp, EndTemp, StepSize;
	float currentC, currentF;
	int count = 0;
	
//If nothing is specified, prompt for inputs 
	if (argc == 1){
		getInputs(&StartTemp, &EndTemp, &StepSize);
	} else if (argc != 4){ 
		puts("Usage: ./tempconverter StartTemp EndTemp StepSize"); //If only partial options, display usage
		return 1;
	} else {
	// If correct number of args, set them
		StartTemp = atoi(argv[1]);
		EndTemp = atoi(argv[2]);
		StepSize = atoi(argv[3]);
	}
	// Check bounds of input
	// Check if Start Temp is above min
	if (StartTemp < START_MIN){
		puts("StartTemp too low");
		return 1;
	} 
	// Check if End Temp is lower than max
	if (EndTemp > END_MAX){
		puts("EndTemp too high");
		return 1;
	}
	// Check if Start Temp is smaller than End temp
	if (StartTemp > EndTemp){
		puts("StartTemp higher than EndTemp");
		return 1;
	}
	if (StepSize == 0)}
		printf("Step size cannot be 0\n");
		return 1;
	}
	if ((StartTemp - EndTemp) > StepSize){
		puts("Step Size greater than difference in temperatures!");
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
