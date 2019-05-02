#james Wei
#cs 3340.501
#Dr. Nguyen
#9 october 2017
#hw 4 textbook problem 2.37 string problem
	.data
string:	.asciiz "1234"
bad_char:	.asciiz "the char in the string was not a number. The value in $v0 will be -1\n"
good_char:	.asciiz "all chars were numbers. The value in $v0 will be "
test:		.asciiz "a"
total:		.word 0 #total of sum
length:		.word 0 #length of string
	.text

main:


	la $a0, string
	li $t5, 0 #let t5 be the length	
count:
	lb $t0, 0($a0) #load byte
	
	addi $t5, $t5, 1 #add 1 to length
	addi $a0, $a0, 1 #increment address by 1
	bne $t0, 0, count #loops

	la $a0, string
	li $t0, 0 #let $t0 hold the byte
	li $t1, 1 #let $t1 be the string increment
	li $t2, 0 #let $t2 be the sum
	li $t3, 0 #let $t3 be the mult holder
	
	add $a0, $a0, $t5 #goes to end of string
	sub $a0, $a0, 2
	sub $t5, $t5, 1
loop:
	lb $t0, 0($a0) #loads byte
	lw $t2, total #let $t4 be the total
	
	beq $t0, 0, good
	blt $t0, 48, bad
	bgt $t0, 57, bad

	sub $t0, $t0, 48 #gets decimal
	mul $t3, $t0, $t1 #gets power of ten
	add $t2, $t2, $t3 #adds new sum to old sum
	sw $t2, total #stores

	mul $t1, $t1, 10 #mult by 10
	sub $a0, $a0, 1 #add 1 to counter
	sub $t5, $t5, 1
	bne $t5, 0, loop

	j good

exit:
	li	$v0, 10 #this ends the program
	syscall
	
bad:
	li $v0, -1
	la $a0, bad_char
	li $v0 4
	syscall
	j exit
	
good:
	la $a0, good_char
	li $v0 4
	syscall
	
	lw $a0, total
	li $v0, 1
	syscall
	
	j exit
