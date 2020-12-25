#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <dlfcn.h>

void replace_memset1(void* mem, int value, size_t num)
{
  void *handle;
  void (*memset1)(void*, int, size_t);
  char *error;

  handle = dlopen ("libmemsets.so", RTLD_LAZY);
  if (!handle) {
      fputs (dlerror(), stderr);
      exit(1);
  }
  (*memset1)(mem, value, num);
  //dlclose(handle);
}

void replace_memset2(void* mem, int value, size_t num)
{
  void *handle;
  void (*memset2)(void*, int, size_t);
  char *error;

  handle = dlopen ("libmemsets.so", RTLD_LAZY);
  if (!handle) {
      fputs (dlerror(), stderr);
      exit(1);
  }
  (*memset2)(mem, value, num);
  //dlclose(handle);
}

void replace_memset_asm(void* mem, int value, size_t num)
{
  void *handle;
  void (*memset_asm)(void*, int, size_t);
  char *error;

  handle = dlopen ("libmemsets.so", RTLD_LAZY);
  if (!handle) {
      fputs (dlerror(), stderr);
      exit(1);
  }
  (*memset_asm)(mem, value, num);
  //dlclose(handle);
}

void check(char* mem, int sz, int val) {
  for (int i = 0; i < sz; i++) {
    if (*(mem + i) != val) {
      printf("Failed at %i (%x)!\n", i, *(mem + i));
      break;
      }
  }
}

int main(int argc, char** argv) {
  unsigned int i, e = 1024, sz = 1024 * 1024;
  clock_t start, stop;
  char* mem = (char*) malloc(sz);

  start = clock();
  for (i = 0; i < e; i++) {
    memset(mem, 61, sz);
  }
  stop = clock();
  printf("Standard memset: %f\n", ((double) stop - start) / CLOCKS_PER_SEC);
  check(mem, sz, 61);

  memset(mem, 0, sz);

  start = clock();
  for (i = 0; i < e; i++) {
    // FIXME: memset1()
    replace_memset1(mem, 61, sz);
  }
  stop = clock();
  printf("Int memset1: %f\n", ((double) stop - start) / CLOCKS_PER_SEC);
  check(mem, sz, 61);
  memset(mem, 0, sz);


  start = clock();
  for (i = 0; i < e; i++) {
    // FIXME: memset2()
    replace_memset2(mem, 61, sz);
  }
  stop = clock();
  printf("Int memset2: %f\n", ((double) stop - start) / CLOCKS_PER_SEC);
  check(mem, sz, 61);


  memset(mem, 0, sz);

  start = clock();
  for (i = 0; i < e; i++) {
    // FIXME: memset_asm()
    replace_memset_asm(mem, 61, sz);
  }
  stop = clock();

  printf("ASM memset: %f\n", ((double) stop - start) / CLOCKS_PER_SEC);
  check(mem, sz, 61);

  memset(mem, 0, sz);

  start = clock();
  for (i = 0; i < e; i++) {
    // FIXME: memset_asm()
    replace_memset_asm(mem, 61, sz);
  }
  stop = clock();

  printf("SSE memset: %f\n", ((double) stop - start) / CLOCKS_PER_SEC);
  check(mem, sz, 61);
  return 0;
}
