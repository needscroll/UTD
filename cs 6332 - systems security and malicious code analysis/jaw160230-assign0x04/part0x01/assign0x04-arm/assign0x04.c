
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <limits.h>

#include "macros.h"
#include "arm_context.h"
#include "arm_disas.h"

/* addresses of asm callout glue code */
extern void *blCallout;
extern void *bCallout;
extern void *bxCallout;
extern void *bgtCallout;
void *callTarget;

extern int user_prog(int);
int a;

void StartProfiling(void *func);

void StopProfiling(void);

void armDecode(uint8_t *ptr, ARMInstr *instr);

void *callTarget;


/*********************************************************************
 *
 *  callout handlers
 *
 *   These get called by asm glue routines.
 *
 *********************************************************************/
void
handleBlCallout(SaveRegs *regs) {
    NOT_IMPLEMENTED();
}

void
handleBCallout(SaveRegs *regs) {
    NOT_IMPLEMENTED();
}

void
handleBxCallout(SaveRegs *regs) {
    NOT_IMPLEMENTED();
}

void
handleBgtCallout(SaveRegs *regs) {
    NOT_IMPLEMENTED();
}

void
handleRetCallout(SaveRegs *regs) {
    NOT_IMPLEMENTED();
}

/*********************************************************************
 *
 *  arm32Decode
 *
 *   Decode an IA32 instruction.
 *
 *********************************************************************/

void
armDecode(uint8_t *ptr, ARMInstr *instr) {
    // TODO: part1
    	instr->len=4;
	if(BITS(*(uint32_t *)ptr, 25, 27) == 0b001 || BITS(*(uint32_t *)ptr, 25, 27 == 0b0))
	{
		instr->opcode = BITS(*(uint32_t *)ptr, 20, 24);
	}
	if (BITS(*(uint32_t *)ptr, 26, 27) == 0b10)
	{
		instr->opcode = BITS(*(uint32_t *)ptr, 20, 25);
	}
	if (BITS(*(uint32_t *)ptr, 26, 27) == 0b01)
	{
		instr->opcode = 65535;
	}

	//printf("address: <%x> len <%i> opcode: <%x> \n", ptr, 4, instr->opcode);



    //NOT_IMPLEMENTED();
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
    /*
     * TODO: Add your instrumentation code here.
     */
	int i = 0;
	while (*(uint32_t *)(func + i) != 0xe12fff1e && i < 2000)
	{
		ARMInstr *instr = malloc(sizeof(ARMInstr));
		armDecode((func+i), instr);
		if (instr->opcode == 65535)
		{
			printf("address: %p, no opcode, len: %d\n", (func+i), instr->len);
		}
		else
		{
			printf("address: %p, opcode %d, len: %d\n", (func+i), instr->opcode, instr->len);
		}
	
		i+= 4;
	}
}

void
StopProfiling(void) {
    /*
     * TODO: Add your instrumentation code here.
     */
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
