#james Wei
#cs 3340.501
#Dr. Nguyen
#24 october 2017
#hw 5

	.data
info_msg:	.asciiz "\nThis program converts between US dollars and Japanese Yen\n"

menu1:		.asciiz "1. sets exchange rate between dollars and yen\n"
menu2:		.asciiz "2. US dollar to Yen conversion\n"
menu3:		.asciiz "3. Yen to US dollar conversion\n"
menu4:		.asciiz "4. exits program\n"
menu_msg:	.asciiz "please select an option from the menu\n"

rate_msg:	.asciiz "please set the exchange rate for US dollars to Japanese Yen\n"
rate_msg2:	.asciiz "the new rate is now "
us_yen_msg:	.asciiz "please enter the amount of us dollars you would like to convert to yen\n"
yen_us_msg:	.asciiz "please enter the amount of yen you would like to convert to us dollars\n"
dollars:	.asciiz " dollars\n"
yen:		.asciiz " yen\n"

newline:	.asciiz "\n"
rate:		.float 115.0 #need change to float?

	.text
loop:
	la $a0, newline #prints newline
	li $v0, 4
	syscall

	la $a0, info_msg #print info msg
	li $v0, 4
	syscall
	
	la $a0, menu1 #prints menu
	li $v0, 4
	syscall
	
	la $a0, menu2 #prints menu
	li $v0, 4
	syscall
	
	la $a0, menu3 #prints menu
	li $v0, 4
	syscall
	
	la $a0, menu4 #prints menu
	li $v0, 4
	syscall
	
	la $a0, menu_msg #prints meny msg
	li $v0, 4
	syscall
	
	li $v0, 5 #gets user input
	syscall

	beq $v0, 1, setrate #sets rate
	beq $v0, 2, us_to_yen #converts us to yen
	beq $v0, 3, yen_to_us #converts yen to us
	beq $v0, 4, exit #exits
	
	j loop
	
exit:
	li	$v0, 10 #this ends the program
	syscall
	
	
setrate:
	la $a0, rate_msg #prints rate msg
	li $v0, 4
	syscall
	
	li $v0, 6 #gets user input float
	syscall
	
	swc1 $f0, rate #stores user rate
	
	la $a0, rate_msg2 #prints ratemsg2
	li $v0, 4
	syscall
	
	lwc1 $f12, rate #prints rate
	li $v0, 2
	syscall
	
	la $a0, newline #prints new line
	li $v0, 4
	syscall
	
	j loop
	
us_to_yen:
	la $a0, us_yen_msg #prints rate msg
	li $v0, 4
	syscall
	
	li $v0, 6 #gets user input and put in $f0
	syscall
	lwc1 $f1, rate #loads rate in $f1
	
	mul.s $f12, $f1, $f0 #mults $f1 (rate) and $f0 (user input) and puts in $f12
	li $v0, 2 #prints $f12
	syscall
	
	la $a0, yen #prints yen
	li $v0, 4
	syscall
	
	j loop
	
yen_to_us:
	la $a0, yen_us_msg #prints rate msg
	li $v0, 4
	syscall

	li $v0, 6 #gets user input and put in $f0
	syscall
	lwc1 $f1, rate #loads rate in $f1
	
	div.s $f12, $f0, $f1 #divs $f0 (user input) and $f1 (rate) and puts in $f12
	li $v0, 2 #prints $f12
	syscall
	
	la $a0, dollars #prints dollars
	li $v0, 4
	syscall

	j loop

