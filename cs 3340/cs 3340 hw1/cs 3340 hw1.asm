#james Wei
#cs 3340.501
#Dr. Nguyen
#5 september 2017
	
	.data
sum_msg:	.asciiz "The sum of A and B (A+B) is \n"
A_msg:	.asciiz "Please enter the value of A \n"
B_msg:	.asciiz "Please enter the value of B \n"
A:	.word 0	#integer A
B:	.word 0	#integer B
C:	.word 0	#integer C

	.text
main:
	la $a0, A_msg #loads A_msg into $a0 to be displayed
	li $v0, 4 #loads display command into $v0
	syscall

	li $v0, 5 #loads get int command into $v0 to be used
	syscall #calls $v0 command, gets int input from user
	
	sw $v0, A #stores word in $v0 to A

	la  $a0, B_msg #loads B_msg into $a0 to be displayed
	li $v0, 4 #loads string display command into $v0
	syscall
	
	li $v0, 5 #loads get int command into $v0 to be used to get second int from user
	syscall #gets int from user
	
	sw $v0, B #stores word from $v0 into B
	
	lw $t0, A #loads A into $t0
	lw $t1, B #loads B into $t1
	add $t2, $t1, $t0 #adds $t1 and $t0 and puts in $t2

	sw $t2, C #stores $t2 in C
	
	la $a0, sum_msg #loads sum_message to be printed
	li $v0, 4 #loads print instruction
	syscall

	lw $a0, C #loads sum_msg into $a0 to be printed
	li $v0, 1 #loads string display command into $v0
	syscall
	
	li	$v0, 10 #this ends the program
	syscall





#use .byte for char
#use .word for ints or 32 bits
#use .asciiz for strings

#syscall does whatever is in $v0. different values in $v0 have different commands
#syscall runs off whatever is in $v0
#(load immediate) li $v0, 4 // loads 4 into $v0. Reads off $a0. 4 means print string / char
#(load immediate) li $v0, 1// loads 1 into $v0. Reads off $a0. 1 means print int
#(load immediate) li $v0, 10// loads 10 into $v0. Reads off $a0. 10 means end program
#(load address) la $a0, sum_msg // loads sum_msg defined in data into $a0
#(load word) lw $a0, A // loads A defined in data as a word into $a0
#li $v0, 5 // on syscall promts user for input, loads into $v0
