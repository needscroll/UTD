#james Wei
#cs 3340.501
#Dr. Nguyen
#9 october 2017
#hw 4

	.data
A:	.word	0 #sum for zip code using recursion
B:	.word	0 #sum for zip code not using recursion
rec_msg:	.asciiz "the sum of all digits using the recursive function is "
nonrec_msg:	.asciiz "the sum of all digits using the non recursive function is "
rec_msg2:	.asciiz " taken from memory location A\n"
nonrec_msg2:	.asciiz " taken from memory location B\n"
ask_msg:	.asciiz "please enter a zip code or 0 to exit\n"
	.text
	
loop:
	la $a0, ask_msg #loads ask zip code message and gets user input in $v0
	li $v0, 4 #displays message
	syscall
	li $v0, 5 #gets user input
	syscall
	beq $v0, 0, exit #exits if $v0 is 0, user input is zero
	
	add $s1, $v0, $zero #saves user input in $s1
	add $a1, $s1, $zero #puts user input in $a1 for input
	jal norec #calls function norec
	
	add $a1, $s1, $zero #puts user input in $a1 for input
	li $a2, 0 #let $a2 be the recursion counter for the rec function
	jal rec #calls function rec
	
	la $a0, nonrec_msg #prints nonrec msg
	li $v0, 4 #displays message
	syscall
	
	lw $a0, B #prints out digit sum for non recursion function
	li $v0, 1
	syscall
	
	la $a0, nonrec_msg2 #prints nonrec msg 2
	li $v0, 4 #displays message
	syscall
	
	la $a0, rec_msg #lprints rec msg
	li $v0, 4 #displays message
	syscall
	
	lw $a0, A #prints out digit sum for recursion function
	li $v0, 1
	syscall
	
	la $a0, rec_msg2 #prints rec msg 2
	li $v0, 4 #displays message
	syscall
	
	li $t0, 0
	sw $t0, A #clears variable A
	
	bne $v0, 0, loop #loops back to beginning
	
exit:
	li	$v0, 10 #this ends the program
	syscall
	
	
	
norec:
	li $t1, 0 #let $t1 be program counter
	li $t2, 10 #let $t2 be divisor
	li $t3, 0 #let $t3 be digit sum
	li $t4, 0 #let $t4 be holder for the remainder
	
calc_nonrec:	
	rem $t4, $a1, $t2 #gets remainder
	add $t3, $t3, $t4 #adds remainder to sum
	div $a1, $a1, $t2 #divide by 10
	
	addi $t1, $t1, 1 #adds 1 to program counter
	bne $t1, 5, calc_nonrec
	
calc_nonrec_exit:
	sw $t3, B #stores digit sum in B
	
	jr $ra #returns

rec:
	addi $sp, $sp, -4 #allocates new space on stack
	sw $ra, 0($sp) #stores return address
	
	li $t1, 0 #let $t1 be program counter
	li $t2, 10 #let $t2 be divisor
	li $t3, 0 #let $t3 be digit sum
	li $t4, 0 #let $t4 be holder for the remainder
	lw $t5, A #let $t5 be variable for digit sum
	
	rem $t4, $a1, $t2 #gets remainder
	add $t3, $t3, $t4 #adds remainder to sum
	div $a1, $a1, $t2 #divide by 10
	
	addi $a2, $a2, 1 #adds 1 to program counter
	
	add $t6, $t5, $t3 #sums total digit sum and new digit sum
	sw $t6, A #stores new digit sum
	
	bne $a2, 5, rec
	
return:
	lw $ra, 0($sp) #restores return address
	addi $sp, $sp, 4 #moves stack pointer back to original position
	
	jr $ra