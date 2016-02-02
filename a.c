#include <stdio.h>
int main() {
	float x = 0.0;
	float guess = 0.0;
	float quot = 0.0;
	int iter = 0;
	scanf("%f", &x);
	guess =  1 ;
	iter =  10 ;
	while ( iter ) {
		quot = ( x / guess );
		guess = ( 0.5 *( guess + quot ));
		iter = ( iter - 1 );
	}
	printf("%f\n",  guess );
	printf("%f\n", ( guess * guess ));
	return 0;
}
