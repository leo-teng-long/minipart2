#include <stdio.h>
int main() {
float x;
float guess;
float quot;
int iter;
scanf("%f", &x);
guess =  1 ;
iter =  10 ;
while ( iter ) {
quot = ( x / guess );
guess = ( 0.5 *( guess + quot ));
iter = ( iter - 1 );
}
printf("%f\n", guess);
return 0;
}
