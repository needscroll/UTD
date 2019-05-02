#james Wei
#cs 3340.501
#Dr. Nguyen
#24 october 2017
#hw 5 pizza
	.data
pi:	.float 3.1415
radius:	.float 4.0
feet:	.float 144.0
prompt:	.asciiz "please enter the amount of pizzas sold in a day\n"
area:	.asciiz "the total square feet of pizza sold today is "
	
	.text
main:
	la $a0, prompt #asks user for pizzas sold
	li $v0, 4
	syscall
	
	li $v0, 6 #gets number of pizzas sold
	syscall
	
	lwc1 $f1, pi #loads pi in $f1
	lwc1 $f2, radius #loads radius in $f2
	lwc1 $f3, feet #loads ratio of square feet to square inches
	mul.s $f12, $f2, $f2 #squares radius
	mul.s $f12, $f12, $f1 #mult squared radius by pi to find area of 1 pizza
	mul.s $f12, $f12, $f0 #mult area of one pizza by amount of pizzas
	div.s $f12, $f12, $f3 #divies by 144 to get square feet
	
	la $a0, area #prints area message
	li $v0, 4
	syscall
	
	li $v0, 2 #prints total square feet
	syscall
	
exit:
	li	$v0, 10 #this ends the program
	syscall
	