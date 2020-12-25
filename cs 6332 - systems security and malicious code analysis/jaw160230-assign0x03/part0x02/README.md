at the beginning of the fib function, we need to make a call to the callout code.
we can't do this directly so we have to do a work around by pushing the callout address to eax and then calling a jump to eax
the following code segment does that


	  *(uint8_t *) func_ptr = 0b8;
	  *(int *)(func_ptr + 1) = &retCallout;
	  *(uint8_t *) (func_ptr + 5) = 0xff;
	  *(uint8_t *) (func_ptr + 6) = 0xd0;