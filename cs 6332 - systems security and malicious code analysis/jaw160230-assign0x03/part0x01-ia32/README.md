by adding a return instruction 0xc3 to the front of the fib function, we can return from the fib function without executing the function code


bonus
(on a technicality, nop + ret is different from just ret and is semantically the same)
1. 
nop
ret
2.
nop
nop
ret
3.
nop
nop
nop
ret
4.
mov eip, eax
jmp eax
5.
add esp,4
jmp dword ptr [esp-4]