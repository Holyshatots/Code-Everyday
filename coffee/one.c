/* Mitch McAffee
Find three digits X, Y and Z such that XYZ in base10 (ten) is equal to ZYX in base9 (nine)?
B
*/

#include <stdio.h>

int base9toBase10(int a, int b, int c)
{

}


int main() 
{
int x,y,z;
x = 0;
y = 0;
z = 0;
// Loop through 0-8 for each number counting up
while((x*100+y*10+z) != base9toBase10(z, y, x)){
	if(z==8){
		z = 0;
		y++;
	} elseif(y==8){
		y = 0;
		x++;
	} elseif(z==8){
		printf("I think something went wrong.\n Went through all numbers without a match.");
		return 1;
	}
	printf("%d %d %d\n", x, y ,z);
}
return 0;
}