#include <stdio.h>
#include <string.h>

char* concatinate(char* s1, char* s2);
char* reverse(char* s1, char* s2);

int main() {
  char* s;
  s = concatinate("Hello", "world");
  printf("%s\n", s);
  return 0;
}

const char* concatinate(char* s1, char* s2) {
  int len1 = strlen(s1);
  int len2 = strlen(s2);
  char s[len1 + len2 + 1];

  int i = 0;
  while (i < len1) {
    s[i] = s1[i];
    i++;
  }
  printf("%s\n", s);
  while (i < len1 + len2 + 1) {
    s[i] = s2[i - len1];
    i++;
  }
  printf("%s\n", s);

  return s;
}
