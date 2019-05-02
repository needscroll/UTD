#james Wei
#cs 3340.501
#Dr. Nguyen
#9 november 2017
#hw 6
	.data
input:	.word 0
ask_msg: .asciiz "Please enter an integer\n"
bit_msg: .asciiz "The number of 1-bit is "




	.text
main:
	la $a0, ask_msg #prints ask msg
	li $v0, 4
	syscall

	li $v0, 5 #gets user input
	syscall

	addi $a0, $v0, 0 #moves input to $a0
	jal bitcount
	addi $t0, $v1, 0 #moves result to $t0
	
	la $a0, bit_msg #prints ask msg
	li $v0, 4
	syscall
	
	addi $a0, $t0, 0 #prints result
	li $v0, 1
	syscall

	li	$v0, 10 #this ends the program
	syscall
bitcount:
	li $v1, 0 #init 0
	li $s0, 32 #loop counter
	beq $a0, 0, zero #exit if loop done
not_zero:
	rem $s1, $a0, 2 #finds remainder
	beq $s1, 0, continue #skips if remainder is 0
	addi $v1, $v1, 1 #adds if remainder is 1
continue:
	subi $s0, $s0, 1 #sub 1 loop counter
	srl $a0, $a0, 1	#div 2
	bne $s0, 0, not_zero #loop if not 0
	j return

zero:
	li $v1, 0 #default if 0
return:
	jr $ra
