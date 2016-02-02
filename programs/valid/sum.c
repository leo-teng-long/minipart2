#include <stdio.h>
#include <string.h>

int main() {
  int x;
  int y;

  scanf("%d", &x);
  y = 0;

  while (x) {
    y = y + x;
    x = x - 1;
  }

  printf("%d\n", y);

  return 0;
}
