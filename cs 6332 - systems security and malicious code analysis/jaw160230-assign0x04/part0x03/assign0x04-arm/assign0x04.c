
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

void StartProfiling(void *func);
void StopProfiling(void);
void armDecode(uint8_t *ptr, ARMInstr *instr);

// global variable.
void *callTarget;

// Original contents of memory that we patch
uint8_t *patchedAddr;
uint32_t patchedFourBytes; // 4 bytes

// Solution prototypes
void patchToCallout(uint8_t *addr, ARMInstr instr);
void restoreInstr();
int patchNextCFlow(uint8_t *);

typedef struct {
	uint8_t * start_address;
	int num_instructions;
} block_t;
block_t blockStruct;


/*********************************************************************
 *
 *  callout handlers
 *
 *   These get called by asm glue routines.
 *
 *********************************************************************/
void
handleBCallout(SaveRegs *regs) {
    //NEED_TO_IMPLEMENT("Inside callout.");
		uint8_t * nextInst = 0;
	nextInst = patchedAddr + 8;
	int32_t offset = 0x00ffffff & patchedFourBytes;
	if (BIT(offset, 23))
	{
		offset |= 0xff000000;
	}
	offset << =2;
	nextInst += offset;
	
	regs->retPC = nextInst;
	if(nextInst != (uint8_t *) &StartProfiling)
	{
		patchNextCFlow(nextInst);
	}
	blockStruct.start_address = nextInst;
}

void
handleBgtCallout(SaveRegs *regs) {

    restoreInstr();
    uint8_t * nextInst = 0;

#define _Z 30
#define _N 31
#define _V 28

    // Branch taken:  [Reason: !Z && N==V]
    if (!BIT(regs->CPSR, _Z) 
            && (BIT(regs->CPSR, _N) == BIT(regs->CPSR, _V))) {
        // branch to the target.
        nextInst = patchedAddr + 8; // due to ARM pipeline.
        int32_t offset = 0x00ffffff & patchedFourBytes;

        // negative value in 2's complement encoding.
        if (BIT(offset, 23)) {
            offset |= 0xff000000;
        }

        offset <<=2;  // 4 bytes align, X4
        nextInst += offset;
    }
    // Ffallthrough to next instruction.
    else {
        nextInst = patchedAddr + 4;
    }

    // return to nextInst
    regs->retPC = nextInst;
    patchNextCFlow(nextInst);
	blockStruct.start_address = nextInst;

}

void
handleBlCallout(SaveRegs *regs) {

    restoreInstr();

    uint8_t * nextInst = 0;
	nextInst = patchedAddr + 8; // due to ARM pipeline.
	int32_t offset = 0x00ffffff & patchedFourBytes;

    // negative value in 2's complement encoding.
    if (BIT(offset, 23)) {
        offset |= 0xff000000;
    }

	offset <<=2;  // 4 bytes align.
	nextInst += offset;

    // return to nextInst
    regs->retPC = nextInst;

    // Endgame condition -- we don't want to patch anymore.
    if (nextInst != (uint8_t *)  &StopProfiling) {
        patchNextCFlow(nextInst);
	}
	blockStruct.start_address = nextInst;

}

void
handleBxCallout(SaveRegs *regs) {
	if ((int) regs->lr <= (int) user_prog)
	{
		patchNextCFlow((uint8_t *) regs->lr);
	}
	blockStruct.start_address = nextInst;
}

void __patchToCallout(uint8_t *addr, ARMInstr instr) {
    // Save original code so we can restore it later
    patchedAddr = addr;
    patchedFourBytes = *(uint32_t * )(addr);

    void *call_target = NULL;
    char *inst_name = NULL;

    uint8_t br_inst = 0;

    if (IS_ARM_BL(instr.opcode)) {
        inst_name = "BL";
        call_target = &blCallout;
        br_inst = ARM_BL;

    } else if (IS_ARM_BX(instr.opcode)) {

        //NEED_TO_IMPLEMENT("transfer to bxCallout");
		inst_name = "BX";
		call_target = &bxCallout;
		br_inst = ARM_B;

    } else if (IS_ARM_B(instr.opcode)) {
        if (instr.cond == ARM_COND_GT ) {        // Greater than
            inst_name = "BGT";
            call_target = &bgtCallout;
            br_inst = ARM_B;

        } else if (instr.cond == ARM_COND_AL) {  // always
            //NEED_TO_IMPLEMENT("transfer to bCallout");
			inst_name = "B";
			call_target = &bCallout;
			br_inst = ARM_B;

        } else {
            // we are only covering instructions from
            // fib()
            NOT_REACHED();
        }
    } else {
        // we are only covering instructions fromn
        // fib()
        NOT_REACHED();
    }

    // offset calculation and little endian encoding.

    int32_t offset = (int32_t) call_target - (int32_t)(addr + 8);
    offset >>= 2; // word (two-bytes) aligned 

    uint8_t *_offset = (uint8_t * ) & offset;

    addr[0] = _offset[0];
    addr[1] = _offset[1];
    addr[2] = _offset[2];
    addr[3] = br_inst; // BL instr.
	
	printf("block address: %p number of instructions: %d\n", blockStruct.start_address, blockStruct.num_instructions);
	blockStruct.num_instructions = 0;

    __builtin___clear_cache((void *) addr, (void *) (addr + 4));
}

/*
 * Restores the code last patched by patch_in_call_to_me. If that function
 * wasn't called, something bad will probably happen.
 */
void restoreInstr() {
    //NEED_TO_IMPLEMENT("unpatch by restoring previously cached instructions.");
	uint8_t *_patchedBytes = (uint8_t *) &patchedFourBytes;
	patchedAddr[0] = _patchedBytes[0];
	patchedAddr[1] = _patchedBytes[1];
	patchedAddr[2] = _patchedBytes[2];
	patchedAddr[3] = _patchedBytes[3];
	
    __builtin___clear_cache((void *) patchedAddr, (void *) (patchedAddr + 8));
}

int patchNextCFlow(uint8_t *addr) {
    ARMInstr instr;
    int cnt = 1;

    while (1) {
		blockStruct.num_instructions += 1;
        armDecode(addr, &instr);
        if (IS_ARM_CFLOW(instr.opcode)) {
            break;
        }
        cnt ++;
        addr += instr.len;
    }

    __patchToCallout(addr, instr);
    return cnt;
}

/*********************************************************************
 *
 *  arm32Decode
 *
 *   Decode an ARM instruction.
 *
 *********************************************************************/

void
armDecode(uint8_t *ptr, ARMInstr *instr) {
    int32_t inst = *((uint32_t *) ptr);

    // condition prefix
    instr->cond =   (uint8_t) BITS(inst, 28, 31);  // 4-bits
    // 2nd and 3rd bytes after condition prefix
    instr->opcode = (uint8_t) BITS(inst, 20, 27);  // 8-bits
    instr->len = 4;
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
	blockStruct.start_address = func;
	blockStruct.num_instructions = 0;
    patchNextCFlow(func);
}

void
StopProfiling(void) {
    NEED_TO_IMPLEMENT("Clean-up needed.");
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

