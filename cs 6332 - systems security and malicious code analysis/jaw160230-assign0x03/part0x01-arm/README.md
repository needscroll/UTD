by adding the following instructions to the front of the fib function we can run these instructions before the rest of fib runs

      *((unsigned char*)(func_ptr + 0)) = 0x0e;
      *((unsigned char*)(func_ptr + 1)) = 0xf0;
      *((unsigned char*)(func_ptr + 2)) = 0xa0;
      *((unsigned char*)(func_ptr + 3)) = 0xe1;
this sets the instructions at the start of the function to a return instruction making the fib function immediately return

bonus:
1.
mov pc, lr
2.
orr r15, r14, r14
3.
add pc, lr, #0x00
4.
sub pc, lr, #0x00
5.
mov pc, #0x00
add pc, pc, lr