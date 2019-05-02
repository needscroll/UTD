#james Wei
#cs 4341.001
#Dr. Cankaya
#29 March 2017
	
	.data
total:		.word 0 #sum of all numbers in array
ask_msg:	.asciiz "\nPlease enter a number\n"
op_msg:		.asciiz "which operation do you want to do\n"
op_msg1:	.asciiz "1. Addition\n"
op_msg2:	.asciiz "2. Subtraction\n"
op_msg3:	.asciiz "3. Multiplication\n"
op_msg4:	.asciiz "4. Division\n"
err_msg:	.asciiz "an error has occured. program will now exit\n"
err_msg_cont:	.asciiz "invalid input\n"
cont_msg:	.asciiz "do you wish to continue or quit? (C/Q)\n"
disp_msg:	.asciiz "the result of the operation is "
newl:		.asciiz "\n"

	.text
main:
	la $a0 ask_msg
	li $v0, 4
	syscall
	
	li $v0, 5
	syscall
	
	move $t1, $v0
	
	li $v0, 4
	syscall
	
	li $v0, 5
	syscall
	
	move $t2, $v0 #at this point $t1 and $t2 contains the two integers
select:
	la $a0, op_msg1
	li $v0, 4
	syscall
	
	la $a0, op_msg2
	li $v0, 4
	syscall
	
	la $a0, op_msg3
	li $v0, 4
	syscall
	
	la $a0, op_msg4
	li $v0, 4
	syscall

	la $a0, op_msg
	li $v0, 4
	syscall #at this point the menu should be displayed
	
	li $v0, 5
	syscall #at this point option should be chosen by user
	
	beq $v0, 1, addition
	beq $v0, 2, subtraction
	beq $v0, 3, multiplication
	beq $v0, 4, division
	
	la $a0, err_msg_cont #selection is not between 1-4, loop back to select
	li $v0, 4
	syscall
	
	j select
	
addition:
	add $t3, $t1, $t2 #$t3 now contains the result
	
	la $a0, disp_msg
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall
	
	la $a0, newl
	li $v0, 4
	syscall
	
	j complete
subtraction:
	sub $t3, $t1, $t2 #$t3 now contains the result
	
	la $a0, disp_msg
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall
	
	la $a0, newl
	li $v0, 4
	syscall
	
	j complete
multiplication:
	mult $t1, $t2 #$t3 now contains the result
	mflo $t3
	
	la $a0, disp_msg
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall
	
	la $a0, newl
	li $v0, 4
	syscall

	j complete
division:
	div $t1, $t2 #$t3 now contains the result
	mflo $t3
	
	la $a0, disp_msg
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall
	
	la $a0, newl
	li $v0, 4
	syscall

	j complete

complete:
	la $a0, cont_msg
	li $v0, 4
	syscall
	
	li $v0, 12
	syscall
	
	beq $v0, 67, main #branches to main if input is 'C'
	beq $v0, 99, main #branches to main if input is 'c'
	
	beq $v0, 81, exit #branches to exit if input is 'Q'
	beq $v0, 113, exit #branches to exit if input is 'q'
	
	la $a0, err_msg_cont
	li $v0, 4
	syscall
	j complete

bad_end:
	la $a0, err_msg
	li $v0, 4
	syscall
	j exit
	
exit:

li	$v0, 10 #this ends the program
	syscall
	
	
	
	
	
	