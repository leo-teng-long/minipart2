#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* scat(char* s1, char* s2);
char* srev(char* s);

int main() {
	float x = 0.0;
	float guess = 0.0;
	float quot = 0.0;
	int iter = 0;
	scanf("%f", &x);
	guess = 1;
	iter = 10;
	while (iter) {
		quot = (x / guess);
		guess = (0.5 * (guess + quot));
		iter = (iter - 1);
	}
	printf("%f\n", guess);
	printf("%f\n", (guess * guess));
	return 0;
}

char* scat(char* s1, char* s2) {
	int l1 = strlen(s1);
	int l2 = strlen(s2);
	char* n = malloc(l1 + l2 + 1);

	int i = 0;
	while (i < l1) {
		n[i] = s1[i];
		i++;
	}
	while (i < l1 + l2) {
		n[i] = s2[i - l1];
		i++;
	}
	n[i] = '\0';

	return n;
}

char* srev(char* s) {
	int i = 0;
	int j = strlen(s) - 1;

	while (i < j) {
		char temp = s[i];
		s[i] = s[j];
		s[j] = temp;
		i++;
		j--;
	}

	return s;
}

