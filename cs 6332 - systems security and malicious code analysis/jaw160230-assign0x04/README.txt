part0x01
For ia32, I used a loop to iterate through the function code until I hit the return statement at the end of the function
for each byte, I checked to see whether it was a valid instruction and if so, decoded the instruction according to the opcode
I printed the opcode, address, and length of the instruction
I repeated this process until I hit the return instruction and the program ended

For arm, I did the same process except the solution was easier since all instructions were 4 bytes long
I calculated the opcode depending on the type of instruction and if the opcode even existed and printed it to the output

part0x02
For ia32, the process was much the same as part 1 except now when encountering a control flow instruction, I patched the code with the callout code
upon being called, I unpatched the code back to the original and the callout emulated the patched code
I abstracted this process to a function patchnextbranch() and also unpatch()

for arm, the process was much the same as the ia32
when encountering a control flow instruction, patch the code to the callout code
when in the handle callout function, unpatch the code back to the original function


part0x03

For ia32, counting the number of instructions and blocks were trivial.
Every time I decoded an instruction, I incremented a counter.
Whenever I reached the end of a block, I printed the number of instructions and reset the counter while adding it to a running total
block addresses were obtained at the same time an instruction was decoded and also printed to the screen

for arm, the process is the exact same as ia32
I incremented a counter for every instruction that was encountered and when I found a block, I printed it to the output
a sum total kept track of the total amount of instructions