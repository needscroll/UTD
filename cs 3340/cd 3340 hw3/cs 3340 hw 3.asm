#james Wei
#cs 3340.501
#Dr. Nguyen
#26 september 2017
	
	.data
total:	.word 0 #sum of all numbers in array
ask_msg:		.asciiz "Please enter a number from 0 to 50.\n"
display_msg:	.asciiz "The sum of integers from 0 to "
display_msg2:	.asciiz " is "
input:		.word 0 #user input
counter:	.word 0 #keeps track of loop
counter1:	.word 0 #counter for second loop
arr:		.word 0 #array from user input
why:		.word 0 #this appears as 1 for some reason
reallywhy:	.word 0 #this is now 2?
test:		.asciiz "test\n"

	.text
main:
	la $a0 ask_msg #loads display message in $a0 to be displayed
	li $v0, 4 #loads display command into $v0
	syscall
	
	li $v0, 5 #loads get int command
	syscall #calls $v0, puts user input into $v0
	
	beq $v0, 0, exit #exits if 0
	
	sw $v0, input #stores $v0 in input

loop:
	lw $t0, counter #let $t0 be counter for the loop
	lw $t2, input #let $t2 be the user input
	la $t3, arr #loads address of array into $t3
	lw $t4, counter #loads counter into $t4

	addi $t0, $t0, 1 #adds 1 to counter

	add $t4, $t4, $t4 #doubles array offset
	add $t4, $t4, $t4 #doubles array offset
	add $t5, $t4, $t3 #gets new array address from offset and initial address
	
	sw $t0, 0($t5) #stores counter in array[counter]
	sw $t0, counter #stores the new counter
	
	slt $t7, $t0, $t2 #let $t7 be 1 if counter < input, else is 0
	beq $t7, 1, loop #loops to loop if counter is less than input

loop2:
	lw $t0, counter1 #loads second counter into $t0
	lw $t2, input #loads input into #t2
	la $t3, arr #loads address of arr into $t3
	lw $t4, counter1 #loads counter1 into $t4
	lw $t6, total #loads total in $t6
	
	addi $t0, $t0, 1 #adds 1 to counter
	
	add $t4, $t4, $t4 #doubles array offset
	add $t4, $t4, $t4 #doubles array offset
	add $t5, $t4, $t3 #gets new array address from offset and initial address

	lw $t1, 0($t5) #loads arr[counter1] to $t1
	add $t6, $t6, $t1 #adds arr[counter1] to total
	
	sw $t6, total #stores the new total
	sw $t0, counter1 #stores the new counter
	
	slt $t7, $t0, $t2 #let $t7 be 1 if counter < input, else is 0
	beq $t7, 1, loop2 #loops to loop if counter is less than input
	
	
	
	
	
	
	la $a0, display_msg #loads the first part of display message
	li $v0, 4 #loads command to display $a0
	syscall
	
	lw $a0, input #loads total to be displayed
	li $v0, 1 #loads command to display $a0
	syscall
	
	la $a0, display_msg2 #loads second part of display message
	li $v0, 4 #loads command to display message
	syscall
	
	lw $a0, total #loads total to be displayed
	li $v0, 1 #loads display int command
	syscall

exit:

li	$v0, 10 #this ends the program
	syscall
