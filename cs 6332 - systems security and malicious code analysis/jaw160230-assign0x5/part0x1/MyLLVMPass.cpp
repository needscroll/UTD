//===- MyLLVMPass.cpp - Example code from "Writing an LLVM Pass" ---------------===//
//
//                     The LLVM Compiler Infrastructure
//
// This file is distributed under the University of Illinois Open Source
// License. See LICENSE.TXT for details.
//
//===----------------------------------------------------------------------===//
//
// This file implements two versions of the LLVM "Hello World" pass described
// in docs/WritingAnLLVMPass.html
//
//===----------------------------------------------------------------------===//

#define DEBUG_TYPE "hello"
#include "llvm/ADT/Statistic.h"
#include "llvm/IR/Function.h"
#include "llvm/Pass.h"
#include "llvm/Support/raw_ostream.h"

#include <llvm/Pass.h>
#include <llvm/PassManager.h>
#include <llvm/ADT/SmallVector.h>
#include <llvm/Analysis/Verifier.h>
#include <llvm/Assembly/PrintModulePass.h>
#include <llvm/IR/BasicBlock.h>
#include <llvm/IR/CallingConv.h>
#include <llvm/IR/Constants.h>
#include <llvm/IR/DerivedTypes.h>
#include <llvm/IR/Function.h>
#include <llvm/IR/GlobalVariable.h>
#include <llvm/IR/InlineAsm.h>
#include <llvm/IR/Instructions.h>
#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/Module.h>
#include <llvm/Support/FormattedStream.h>
#include <llvm/Support/MathExtras.h>
#include <algorithm>

#include "llvm/IR/IRBuilder.h"

using namespace llvm;

StringRef fname;

namespace {
  // Hello - The first implementation, without getAnalysisUsage.
  struct MyLLVMPass : public ModulePass {
    static char ID; // Pass identification, replacement for typeid

    MyLLVMPass() : ModulePass(ID) {
    }

    virtual bool runOnModule(Module &M) {
        //lists all global variables
        errs() << "Global Variables Listaaa:\n";
        for (GlobalVariable& gv : M.getGlobalList()) {
            errs() << "\t" << gv << "\n";
        }
        errs() << "=============================================\n";
        //lists all functions and prototypes
        errs() << "Functions List:\n";
        for (Function& fun : M) {
          //name
          errs() << "\t" << fun.getName() << ":\n";
          //input parameters.
          errs() << "\tInput Parameters:\n";
          for (Argument& a : fun.getArgumentList()) {
              errs() << "\t\t" << a << "\n";
          }
          //return type.
          errs() << "\treturn Type: " << *fun.getReturnType() << "\n";
          //size
          errs() << "\tsize of blocks: " << fun.size() << "\n";
          //details for blocks
          for (BasicBlock& b : fun) {
              // Number of instructions included.
              errs() << "\t\tnumber of instructions: " << b.size() << "\n";
              // Instruction summary
              for (Instruction& i : b) {
                  errs() << "\t\tInstName: " << i.getOpcodeName() << ", Operand #: " << i.getNumOperands() << "\n";
              }
          }
          errs() << "=============================================\n";
        }

        bool changed = false;
        //errs() << "Hello, I am in the MyLLVMPass \n";
        return changed;
    }
  };
}

char MyLLVMPass::ID = 0;
static RegisterPass<MyLLVMPass> X("MyLLVMPass", "MyLLVMPass");
