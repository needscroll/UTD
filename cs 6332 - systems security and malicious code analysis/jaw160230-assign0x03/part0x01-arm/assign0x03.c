
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <limits.h>

#include "macros.h"
#include "arm_context.h"

/* addresses of asm callout glue code */

extern void* retCallout;


int fib();

/*********************************************************************
 *
 *  callout handler
 *
 *   These get called by asm glue routines.
 *
 *********************************************************************/

void
handleRetCallout(SaveRegs regs)
{
   NOT_IMPLEMENTED();
}

/*********************************************************************
 *
 *  StartProfiling, StopProfiling
 *
 *   Profiling hooks. This is your place to inspect and modify the profiled
 *   function.
 *
 *********************************************************************/

void
StartProfiling(void *func)
{
    uint8_t *func_ptr;
    func_ptr = (uint8_t *) func;

    /*
     * TODO: Add your instrumentation code here.
     */

      //need to be in function call or it will not work
      if(change_page_permissions_of_address(func_ptr) == -1) {
          fprintf(stderr, "Error while changing page permissions of fib(): %s\n", strerror(errno));
      }

      *((unsigned char*)(func_ptr + 0)) = 0x0e;
      *((unsigned char*)(func_ptr + 1)) = 0xf0;
      *((unsigned char*)(func_ptr + 2)) = 0xa0;
      *((unsigned char*)(func_ptr + 3)) = 0xe1;
}

int main(int argc, char *argv[])
{
   int value;
   char *end;

   if (argc != 2) {
      fprintf(stderr, "usage: %s <value>\n", argv[0]);
      exit(1);
   }

   value = strtol(argv[1], &end, 10);

   if (((errno == ERANGE)
        && ((value == LONG_MAX) || (value == LONG_MIN)))
       || ((errno != 0) && (value == 0))) {
      perror("strtol");
      exit(1);
   }

   if (end == argv[1]) {
      fprintf(stderr, "error: %s is not an integer\n", argv[1]);
      exit(1);
   }

   if (*end != '\0') {
      fprintf(stderr, "error: junk at end of parameter: %s\n", end);
      exit(1);
   }

   StartProfiling(fib);

   value = fib(value);
   printf("%d\n", value);
   exit(0);
}

int fib(int i)
{
   if (i <= 1) {
      return 1;
   }
   return fib(i-1) + fib(i-2);
}

int change_page_permissions_of_address(void *addr) {
    // Move the pointer to the page boundary
    int page_size = getpagesize();
    addr -= (unsigned long)addr % page_size;

    if(mprotect(addr, page_size, PROT_READ | PROT_WRITE | PROT_EXEC) == -1) {
        return -1;
    }

    return 0;
}
