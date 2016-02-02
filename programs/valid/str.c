#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
  int l = strlen(s);
  char* n = malloc(l + 1);
  int k = 0;
  while (k < l) {
    n[k] = s[k];
    k++;
  }
  n[k] = '\0';
  int i = 0;
  int j = l - 1;
  while (i < j) {
    char temp = n[i];
    n[i] = n[j];
    n[j] = n[i];
    i++;
    j--;
  }
  return n;
}

int main() {
  char s1[128];
  char s2[128];

  scanf("%s", s1);
  scanf("%s", s2);

  printf("%s\n", srev(scat(s1, s2)));

  return 0;
}
