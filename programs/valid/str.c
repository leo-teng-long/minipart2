#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* scat(char* s1, char* s2);
char* srev(char* s);

int main() {
  char s1[128];
  char s2[128];
  scanf("%s", s1);
  scanf("%s", s2);

  printf("%s\n", srev(scat(s1, s2)));

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
