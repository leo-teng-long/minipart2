#include <stdio.h>

int main() {
  int x = 0;
  scanf("%d", &x);

  if (x) {
    printf("%d\n", x);
  } else {
    printf("%d\n", 0);
  }

  return 0;
}
