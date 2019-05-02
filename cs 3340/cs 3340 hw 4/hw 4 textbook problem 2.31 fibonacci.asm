#james Wei
#cs 3340.501
#Dr. Nguyen
#9 october 2017
#hw 4 textbook problem 2.31

	.data
	
	
	.text
main:
	li $a0, 7
	jal fib
	
	move $a0, $v0
	li $v0, 1
	syscall
	
exit:
	li	$v0, 10 #this ends the program
	syscall
	
fib:
	beq $a0, 0, zero #exit if 0
	beq $a0, 1, one #exit if 1
	
other:
	sub $sp, $sp, 4 #move down stack
	sw $ra, 0($sp) #store return address on stack

	sub $a0,$a0,1 #sub 1 from $a0 for n-1
	jal fib

	lw $ra, 0($sp) #load return address

	sw $v0,0($sp) #store value into $v0

	sub $sp,$sp,4   #move down stack
	sw $ra,0($sp) #store return address on stack

	sub $a0, $a0, 1 #sub 1 from $a0 again for n-1
	jal fib
	add $a0, $a0, 2

	lw $ra, 0($sp) #load return address from stack
	add $sp, $sp, 4 #move down stack

	lw $s7, 0($sp) #load return address from stack
	add $sp, $sp, 4 #move down stack

	add $v0, $v0, $s7 #add values together, put in $v0

	j fib_ret
zero:
	li $v0, 0
	j fib_ret
one:
	li $v0, 1
	j fib_ret
fib_ret:
	jr $ra	
