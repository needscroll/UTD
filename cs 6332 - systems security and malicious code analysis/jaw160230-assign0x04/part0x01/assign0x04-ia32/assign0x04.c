#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <limits.h>

#include "macros.h"
#include "ia32_context.h"
#include "ia32_disas.h"

/* addresses of asm callout glue code */

extern void *jccCallout;
extern void *jmpCallout;
extern void *callCallout;
extern void *retCallout;

extern uint32_t ia32DecodeTable[]; /* see below */

/* instrumentation target */

extern int user_prog(int);

void StartProfiling(void *func);

void StopProfiling(void);

void ia32Decode(uint8_t *ptr, IA32Instr *instr);

void *callTarget;

/*********************************************************************
 *
 *  callout handlers
 *
 *   These get called by asm glue routines.
 *
 *********************************************************************/

void
handleJccCallout(SaveRegs regs) {
    NOT_IMPLEMENTED();
}

void
handleJmpCallout(SaveRegs regs) {
    NOT_IMPLEMENTED();
}

void
handleCallCallout(SaveRegs regs) {
    NOT_IMPLEMENTED();
}

void
handleRetCallout(SaveRegs regs) {
    NOT_IMPLEMENTED();
}



/*********************************************************************
 *
 *  ia32Decode
 *
 *   Decode an IA32 instruction.
 *
 *********************************************************************/

 #define IS_DECODE(_a, _b)   (ia32DecodeTable[ (_a) ]& (_b) )

 void
 ia32Decode(uint8_t *ptr, IA32Instr *instr)
 {
    instr->len=0;
    int i = 0;
  //parse prefixes
    while (IS_DECODE(ptr[i], IA32_PREFIX)) {
         instr->len+=1;
         i++;
    }

    //processing opcode size 1 byte or 2 byte case
    if (IS_DECODE(ptr[i],IA32_2BYTE)) {
        if (ptr[i]==0x0f) {
             instr ->len += 2;
             instr->opcode = (uint16_t) ptr[i];
             i+=2;
         } else {
             instr ->len += 1;
             instr->opcode = ptr[i];
             i++;
         }
     }
     else
     {
       instr->len+=1;
       instr->opcode = ptr[i];
       i++;

     }

    if (IS_DECODE(instr->opcode, IA32_MODRM)) {

         switch (BITS(ptr[i],6,7)){  // Checking MOD value
             case 0b00:  // 0
                 instr->len+=1;
                 i++;
                 if (BITS(ptr[i],0,2) == 4) {
                   instr->len+=1;
                   i++;
                 }
                 if (BITS(ptr[i],0,2) == 5) {
                   instr->len+=4;
                   i+=4;
                 }
                 break;
             case 0b01:     // 1
                instr->len+=2;
                i+=2;
                if (BITS(ptr[i],0,2) == 4) {
                  instr->len+=1;
                  i++;
                }
                break;
             case 0b10:     // 2
                instr->len+=6;
                i+=6;
                break;
             case 0b11:     // 3
                instr->len+=1;
                i++;
                 break;
         }
     } else {
         instr->len+=0;
     }

     if (IS_DECODE(instr->opcode, IA32_IMM8)) {
         instr->len+=1;
     } else if (IS_DECODE(instr->opcode, IA32_IMM32)){
         instr->len+=4;
     }
     //printf("address: %x, opcode: %x, len:%d", ptr, instr->opcode, instr->len);
    return;
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
StartProfiling(void *func) {
    int i = 0;
    while(*(uint8_t *)(func + i) != 0xc3) {
      IA32Instr *instr = malloc(sizeof(IA32Instr));
      ia32Decode((func+i), instr);
      printf("address: %x, opcode: %x, len: %d\n", *(uint8_t *)(func+i), instr->opcode, instr->len);
      i+=instr->len;
    }
    IA32Instr *instr = malloc(sizeof(IA32Instr));
    ia32Decode((func+i), instr);
    printf("address: %x, opcode: %x, len: %d\n", *(uint8_t *)(func+i), instr->opcode, instr->len);
    i+=instr->len;
}

void
StopProfiling(void) {
    ;
}

int main(int argc, char *argv[]) {
    int value;
    char *end;

    char buf[16];

    if (argc != 1) {
        fprintf(stderr, "usage: %s\n", argv[0]);
        exit(1);
    }

    printf("input number: ");
    scanf("%15s", buf);

    value = strtol(buf, &end, 10);

    if (((errno == ERANGE)
         && ((value == LONG_MAX) || (value == LONG_MIN)))
        || ((errno != 0) && (value == 0))) {
        perror("strtol");
        exit(1);
    }

    if (end == buf) {
        fprintf(stderr, "error: %s is not an integer\n", buf);
        exit(1);
    }

    if (*end != '\0') {
        fprintf(stderr, "error: junk at end of parameter: %s\n", end);
        exit(1);
    }

    StartProfiling(user_prog);

    value = user_prog(value);

    StopProfiling();

    printf("%d\n", value);
    exit(0);
}
