# 11/30/17
# CS 3340.501 Team Project - Reversi
# Program Description : Implement the Reversi game using MIPS assembly language. 
#
# Program by: Tristen Even - tge160130, Dat Vo - qvv160030, Kyle Bailey - kdb160630, James Wei - jaw160230
#
##########################################################
#	    		MUST READ:			 #
##########################################################
#  	      BitMap Display Tool Settings :		 #
#  	      unit width in pixels : 32			 #
#  	      unit width in pixels : 32			 #
# 	      display width in pixels : 256	  	 #
# 	      display height in pixels : 256		 #
# 	      base address for display: 0x10008000($gp)  #
##########################################################


#variable table
#t0: firstturn
#t2: validinput

#s0: O
#s1: X
#s3: column
#s4: row
#s5: who's turn, 0 for human, 1 for comp

#pseudocode
#cout << welcome
#t0 = random()
#if(t0 ==0)
#	computer is first
#else
#	player is first
#printboard
#cin >> player input
#while(possible moves available)
#if(isInputValid)
#  if(move(col,row))
#  branch to computer turn
#
#computer turn:
#for(each row,col)
#   if(move(col,row, computer))
#   break
#continue
#
#isInputValid(col, row) {
#if(val(col,row) == '-'|| 'O' || 'X')
# return false
#}
#
# move(col, row) {
# for(eachdirection) {
#     while(found opponent piece) {
#     look at next piece
#     }
#     if(see a '-' || out of bounds)
#    	 return false
#    else
#        while(not back to beginning)
#            flip pieces
#	return true
#}


.data
welcomeMessage:	.ascii "\n  __      __        __                               \n"
		.ascii " /  \\    /  \\ ____ |  |   ____  ____   _____   ____  \n"
		.ascii " \\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \n"
		.ascii "  \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \n"
		.ascii "   \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  > \n"
		.ascii "        \\/       \\/          \\/            \\/     \\/  \n"
		.ascii "\nThe following Reversi game was made possible by Kyle, Tristen, James, and Dat. \n"
		.ascii "\nDetermining whether player or computer goes first... \n"
		.asciiz "\nPress any key to continue. \n"
playersTurn:	.asciiz "\nCongratulations! You are moving first.\n"
computersTurn:	.asciiz "\nComputer will be moving first. \n"
playerWon:	.asciiz "\nYou Won!"
computerWon:	.asciiz "\nAww, the computer has won..."
noMovesPlayer:	.asciiz "\nThere are no more remaining moves for you. "
noMovesComputer:.asciiz "\nThere are no more remaining moves for the computer. "
drawMessage:	.asciiz "Whoa it's a draw!"
passTurn:	.ascii "\nIf you wish to pass on this turn, please enter 'p'. \n"
		.asciiz "\nWhat is your move?: "
invalidError:	.ascii  "\n.___                    .__  .__    .___\n"
		.ascii	"|   | _______  _______  |  | |__| __| _/\n"
		.ascii	"|   |/    \\  \\/ /\\__  \\ |  | |  |/ __ | \n"
		.ascii	"|   |   |  \\   /  / __ \\|  |_|  / /_/ | \n"
		.ascii	"|___|___|  /\\_/  (____  /____/__\\____ | \n"
		.ascii	"         \\/           \\/             \\/ \n"
		.ascii "\nInput was invalid.\n"
		.ascii "\nInput your move by starting with the column followed by the row. (ex: f5) \n"
		.asciiz "\nPlease try again. \n"
illegalError:	.ascii "\n.___.__  .__                      .__ \n"
		.ascii "|   |  | |  |   ____   _________  |  | \n"
		.ascii "|   |  | |  | _/ __ \ / ___\__  \ |  |  \n"
		.ascii "|   |  |_|  |_\  ___// /_/  > __ \|  |__\n"
		.ascii "|___|____/____/\___  >___  (____  /____/\n"
		.ascii "                   \/_____/     \/      \n"
		.ascii "\nThat move was illegal.\n"
		.ascii "\nYou can only place a chip where you flank at \n"
		.ascii "least one of your opponents chip between the new \n"
		.ascii "chip and your own chip in the vertical, horizontal, or diagonal direcction \n"
		.asciiz "\nPlease try again.\n"
nextLine:	.asciiz "\n\n\n"

#	Bitmap Display Data	#
colorWhite: 		.word	0x00FFFFFF # X
colorBlack: 		.word	0x000000   # O
colorDarkGreen:		.word	0x007F0E
colorLightGreen:	.word	0x4CFF00
debug0:			.asciiz	"Value of value to set : "

columnID:	.asciiz "\n  a|b|c|d|e|f|g|h\n"
row1:		.ascii "1 -|-|-|-|-|-|-|-\n"
		.ascii "2 -|-|-|-|-|-|-|-\n"
		.ascii "3 -|-|-|-|-|-|-|-\n"
computerRow:	.ascii "4 -|-|-|X|O|-|-|-\n"
		.ascii "5 -|-|-|O|X|-|-|-\n"
		.ascii "6 -|-|-|-|-|-|-|-\n"
		.ascii "7 -|-|-|-|-|-|-|-\n"
	       	.asciiz "8 -|-|-|-|-|-|-|-"
continueKey:	.space    1

.text
main:

# Print the welcome message
li	$v0, 4
la	$a0, welcomeMessage
syscall
# store ascii value for 'O' into s0
li	$s0, 79
# store ascii value for 'X' into s1
li	$s1, 88
# press any key to continue
li	$v0, 12
la	$a0, continueKey
syscall
#------------------------#
#| Print the ascii board |
#------------------------#
li	$v0, 4
la	$a0, columnID
syscall

li	$v0, 4
la	$a0, row1
syscall

la	$a0, nextLine
syscall
#-----------------------#
jal	drawBoard		#Bitmap Display function
jal	drawStartPositions	#Bitmap Display function

# generates random int
li $v0, 42
# a1 holds the upper bound of 2
la $a1, 2
# randomly generated number will be at $a0
syscall
# if random number is 0, comp is first
# else, player is first
beq 	$a0, $zero, computerStart
b	playerStart

computerStart:
# print computer's turn message
li	$v0,	4
la	$a0,	computersTurn
syscall

# load ascii value for X into t4
li	$t4,	88
# load the first move's row and place X in f4
la	$t5,	computerRow
add	$t5,	$t5,	12
sb	$t4($t5)

# Bitmap Display addition <==========================================================
# Writtened by Kyle Bailey
addiu	$sp,	$sp,	-16	#allocate 1 word to stack
sw	$ra,	0($sp)		#save $ra to stack
sw	$a0,	4($sp)		#save $a0 to stack
sw	$a1,	8($sp)		#save $a1 to stack
sw	$a2,	12($sp)		#save $a2 to stack
addi	$a0,	$zero,	6
addi	$a1,	$zero,	4
addi	$a2,	$zero,	88
jal	drawSquare
lw	$a2,	12($sp)		#restore $a2 from stack
lw	$a1,	8($sp)		#restore $a1 from stack
lw	$a0,	4($sp)		#restore $a0 from stack
lw	$ra,	0($sp)		#restore $ra from stack
addiu	$sp,	$sp,	16	#release 1 word from stack
# End of Bitmap Display addition <===================================================

# load ascii value for X into t4
li	$t4,	88
# load the first move's row and place X in e4
la	$t5,	computerRow
add	$t5,	$t5,	10
sb	$t4($t5)

# Bitmap Display addition <==========================================================
# Writted by Kyle Bailey
addiu	$sp,	$sp,	-16	#allocate 1 word to stack
sw	$ra,	0($sp)		#save $ra to stack
sw	$a0,	4($sp)		#save $a0 to stack
sw	$a1,	8($sp)		#save $a1 to stack
sw	$a2,	12($sp)		#save $a2 to stack
addi	$a0,	$zero,	5
addi	$a1,	$zero,	4
addi	$a2,	$zero,	88
jal	drawSquare
lw	$a2,	12($sp)		#restore $a2 from stack
lw	$a1,	8($sp)		#restore $a1 from stack
lw	$a0,	4($sp)		#restore $a0 from stack
lw	$ra,	0($sp)		#restore $ra from stack
addiu	$sp,	$sp,	16	#release 1 word from stack
# End of Bitmap Display addition <===================================================

# load column row
li	$v0, 4
la	$a0, columnID
syscall
# load rest of rows
li	$v0, 4
la	$a0, row1
syscall
# branch to letsPlay
b 	letsPlay

playerStart:
# print player's turn message
li	$v0, 4
la	$a0, playersTurn
syscall

letsPlay:
# print message for player to pass
li	$v0, 4
la	$a0, passTurn
syscall
# get player's input
li	$v0, 12
syscall
# store player's input in s3
move	$s3, $v0
# if input is 'p', player has passed (goto that branch)
beq	$s3, 112, playerPassed
# read player's input as a move
li	$v0, 12
syscall

# store the row part of player's move into s4
move	$s4, $v0
# subtract by 48 to turn player's row char into an int
sub	$s4, $s4, 48
# if int value is 0, then quit
beq 	$s4, $zero, Exit

# allocate space on stack
addiu 	$sp, $sp, -16
# store the column
sw	$s3, 0($sp)
# store the row
sw	$s4, 4($sp)
# store the return address
sw	$ra, 12($sp)
# check if valid
jal 	isInputValid
# load return address
lw	$ra, 12($sp)
# load return value
lw	$t2, 8($sp)
# pop the stack
addiu	$sp, $sp, 16
# if t2 is 0, then it's an invalid input
bne $t2, $zero, invalidInput
# not invalid so game on and move to phase one
b	playPhaseOne

invalidInput:
# print invalid input message
li	$v0, 4
la	$a0, invalidError
syscall
# play error sound and branch back to play again
jal	error_sound
b	letsPlay

playPhaseOne:

li	$s5, 0
# allocate space on stack to test player's move
addiu	$sp, $sp, -12
sw	$s5, 0($sp)
sw	$ra, 8($sp)
jal	isMoveLegal
# return value holds whether or not move was valid
# load return value and address
lw	$v0, 4($sp)
lw	$ra, 8($sp)
# if move was illegal
bne $v0, $zero, illegalErrorMessage
# else move to phase two
b playPhaseTwo

playPhaseTwo:
# print the board
li	$v0, 4
la	$a0, nextLine
syscall
li	$v0, 4
la	$a0, columnID
syscall
la	$a0, row1
syscall
jal	ding_sound
b	playComputersTurn

playerPassed:
# s7 is the boolean indicator if player passed
li	$s7, 1

playComputersTurn:
# holds count for possible move in each column
li	$s3, 97
# holds count for possible move in each row
li	$s4, 1
li	$s5, 1

possibleMovesPCLoop:
# once counts greater than range, no more possible moves
bge	$s3, 105, possibleMovesPCResetColumn
bge	$s4, 9, possibleMovesPCNone
# allocate space on stack to check input
addiu $sp, $sp, -16
# store column
sw	$s3, 0($sp)
# store row
sw	$s4, 4($sp)
# store return address
sw	$ra, 12($sp)
jal 	isInputValid
# load return value and address
lw	$ra, 12($sp)
# t0 has return value
lw	$t0, 8($sp)
# pop the stack
addiu	$sp, $sp, 16
# if t0 = 0 (invalid move), try again
bne	$t0, $zero, possibleMovesPCInvalid
# since valid move, use stack to save row/column
addiu	$sp, $sp, -12
sw	$s5, 0($sp)
sw	$ra, 8($sp)
# make the move
jal	isMoveLegal
# load return value and address
lw	$v0, 4($sp)
lw	$ra, 8($sp)

possibleMovesPCInvalid:
# increment for to try move in next column
add	$s3, $s3, 1
# if another invalid move, try aagain
bne $v0, $zero, possibleMovesPCLoop
# if valid move, computer's turn is done
b	computersTurnEndPhase

possibleMovesPCResetColumn:
# reset column count
li	$s3, 97
# increment row count (try next row) and repeat
add	$s4, $s4, 1
b	possibleMovesPCLoop

possibleMovesPCNone:
# print no moves for computer message
li	$v0, 4
la	$a0, noMovesComputer
syscall

# end the game, if player passed on previous turn
beq	$s7, 1, noMovesPC
b	computersTurnEndPhase

noMovesPC:
# print no moves left message
li	$v0, 4
la	$a0, noMovesPlayer
syscall
# find out who won
b	countChips

computersTurnEndPhase:
# print the board
li	$v0, 4
la	$a0, nextLine
syscall
li	$v0, 4
la	$a0, columnID
syscall
la	$a0, row1
syscall

b letsPlay

# Exits the program
Exit:
li	$v0, 10
syscall

countChips:
# first instruct. holds the column count
# second instruct. holds the row count
li	$s3, 97
li	$s4, 1
# t5 holds count for 'O'
# t9 holds count for 'X'
li	$t5, 0
li	$t9, 0

# loop to count total chips own by player & computer
countChipsLoopOne:
# if row count >= 9, done counting all chips
bge	$s4, 9, countChipsEndPhase
# allocate space on stack for counting
addiu   $sp, $sp, -28
# load board into t8 and store on stack
la	$t8, row1
sw	$t8, 0($sp)
# store column
sw	$s3, 4($sp)
# store row
sw	$s4, 8($sp)
# store 'O' and 'X' count
sw	$t9, 16($sp)
sw	$t5, 20($sp)
# store return address
sw	$ra, 24($sp)
# get whether it's 'X' or 'O' at the position
jal 	readValueAtCR
# load address and count totals
lw	$ra, 24($sp)
lw	$t9, 16($sp)
lw	$t5, 20($sp)
# t2 is the return value
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 28
# if return value is 'O' or 'X'
beq	$t2, 79, countChipsPlayer
beq	$t2, 88, countChipsPC
# goto phase two of loop
b	countChipsLoopColumns

countChipsPlayer:
# increment 'O' count
add	$t5, $t5, 1
b	countChipsLoopColumns

countChipsPC:
# increment 'X' count
add	$t9, $t9, 1
b	countChipsLoopColumns

countChipsLoopColumns:
# increment column count
add	$s3, $s3, 1
# if reach end of column
bge	$s3, 105, countChipsLoopNextRow
# else go back to first endphase loop
b	countChipsLoopOne

countChipsLoopNextRow:
# reached end of columns
# reset column count
li	$s3, 97
#increment row count
add	$s4, $s4, 1
# go back to first endphase loop
b	countChipsLoopOne

countChipsEndPhase:
# done with counting chips
# if t5 > t9, player won
bgt	$t5, $t9, gamePlayerWon	#player won
# if t5 = t9, it's a draw
beq	$t5, $t9, gameDrawed
# else computer won
b	gamePCWon

gamePlayerWon:
# print player won message and play sound and exits
li	$v0, 4
la	$a0, playerWon
syscall
jal victory_sound
b	Exit


gameDrawed:
# print drawed game message and exits
li	$v0, 4
la	$a0, drawMessage
syscall
b	Exit

gamePCWon:
# print computer won message and exits
li	$v0, 4
la	$a0, computerWon
syscall
b	Exit


illegalErrorMessage:
# print error message, plays sounds, and try again
li	$v0, 4
la	$a0, illegalError
syscall
jal error_sound
b  	letsPlay

# get the value at col,row
readValueAtCR:
# load ra into t9
lw	$t9, 0($sp)
# load column value
lw	$t7, 4($sp)
# load row value 
lw	$t2, 8($sp)

# subtract by 96 to change column (a -> h) to (1 -> 8)
sub	$t7, $t7, 96
# subtract 1 to fix one-off error
sub	$t2, $t2, 1
# t5 hold totals chars in a row
li	$t5, 18
# multiply totals chars to get offset with column
mult	$t5, $t2
# move to t2 (row)
mflo	$t2
# multiply column by 2 to skip spaces
sll	$t7, $t7, 1
# add offset for column
add	$t9, $t9, $t7
# add offset for row
add	$t7, $t9, $t2
# set value from address
lb	$s6($t7)
# store return value on stack
sw	$s6, 12($sp)
# return
jr	$ra

#set the value at col,row
setValueAtCR:
# load ra into t9
lw	$t9, 0($sp)
# load column value
lw	$t7, 4($sp)
# load row value into t2
lw	$t2, 8($sp)
# load the value from previous function to set
lw	$s6, 12($sp)
# t5 hold totals chars in a row
li	$t5, 18
# subtract by 96 to change column (a -> h) to (1 -> 8)
sub	$t7, $t7, 96
# subtract 1 to fix one-off error
sub	$t2, $t2, 1
# multiply totals chars to get offset with column
mult	$t5, $t2
# move to t2 (row)
mflo	$t2
# multiply column by 2 to skip spaces
sll	$t7, $t7, 1
# add offset for column
add	$t9, $t9, $t7
# add offset for row
add	$t7, $t9, $t2
#set value from address
sb	$s6($t7)

# Bitmap Display addition <==========================================================
# Written by Kyle Bailey
addiu	$sp,	$sp,	-16	#allocate 1 word to stack
sw	$ra,	0($sp)		#save $ra to stack
sw	$a0,	4($sp)		#save $a0 to stack
sw	$a1,	8($sp)		#save $a1 to stack
sw	$a2,	12($sp)		#save $a2 to stack
lw	$a0,	20($sp)		#column
lw	$a1,	24($sp)		#row
lw	$a2,	28($sp)		#char to set to
sub	$a0,	$a0,	96	#to convert [a-h] to [1-8]
jal	drawSquare
lw	$a2,	12($sp)		#restore $a2 from stack
lw	$a1,	8($sp)		#restore $a1 from stack
lw	$a0,	4($sp)		#restore $a0 from stack
lw	$ra,	0($sp)		#restore $ra from stack
addiu	$sp,	$sp,	16	#release 1 word from stack
# End of Bitmap Display addition <===================================================

# return
jr	$ra

#checkingput(col, row, valid)
#returns 0 if valid, 1 otherwise
isInputValid:
# load column value
lw	$t7, 0($sp)
# load row value
lw	$t2, 4($sp)
# checks if within column/row range
# else return error
# subtract column value by 97 for lower bound
sub	$t9, $t7, 97
bltz	$t9, addErrorToStack
# column value > 104 for upper bound
bgt	$t7, 104, addErrorToStack

# if greater than row's lower bound
blez	$t2, addErrorToStack
# if less than row's upper bound
bgt	$t2, 8, addErrorToStack

# allocate space on stack for to get value at (column,row)
addiu $sp, $sp, -20
# load board into t0
la	$t0, row1
sw	$t0, 0($sp)
# store column 
sw	$s3, 4($sp)
# store row 
sw	$s4, 8($sp)
# store return address
sw	$ra, 16($sp)
# get value of the location
jal 	readValueAtCR
# load return value and address
# t2 has return value
lw	$ra, 16($sp)
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20

# if value isn't '-'
bne	$t2, 45, addErrorToStack
# t9 flag, ready for input
li	$t9, 0
b	returnToStack

addErrorToStack:
# load t9 for stack
li	$t9, 1

returnToStack:
# put value into stack
sw	$t9, 8($sp)
# return
jr	$ra

# checks if move is legal, 0 = legal, 1 = illegal
# checks all 8 directions
isMoveLegal:
# t0 holds if move was legal, begin by assuming illegal
li	$t0, 1

isRightDirLegal:
# allocate space on stack to check right
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check right
jal	checkRightDir
# return if right was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8

# if return = 0, right was legal
beq 	$v0, $zero, rightLegalFlag
# else not legal, check bottom right
b	isBotRightLegal

# flag to indicate right was legal
rightLegalFlag:
li	$t0, 0

isBotRightLegal:
# allocate space on stack to check bottom right
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check bottom right
jal	checkBotRightDir
# return if bottom right was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, bottom right was legal
beq 	$v0, $zero, botRightLegalFlag
# else not legal, check bottom
b	isBottomLegal

# flag to indicate bottom right was legal
botRightLegalFlag:
li	$t0, 0

isBottomLegal:
# allocate space on stack to check bottom
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check bottom
jal	checkBottomDir
# return if bottom was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, bottom right was legal
beq 	$v0, $zero, bottomLegalFlag
# else not legal, check bottom
b 	isBotLeftLegal

# flag to indicate bottom right was legal
bottomLegalFlag:
li	$t0, 0

isBotLeftLegal:
# allocate space on stack to check bottom left
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check bottom left
jal	checkBotLeftDir
# return if bottom left was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, bottom left was legal
beq 	$v0, $zero, botLeftLegalFlag
# else not legal, check left
b 	isLeftLegal

# flag to indicate bottom left was legal
botLeftLegalFlag:
li	$t0, 0

isLeftLegal:
# allocate space on stack to check left
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check left
jal	checkLeftDir
# return if left was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, left was legal
beq 	$v0, $zero, leftLegalFlag
# else not legal, check top left
b	isTopLeftLegal

# flag to indicate left was legal
leftLegalFlag:
li	$t0, 0

isTopLeftLegal:
# allocate space on stack to check top left
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check top left
jal	checkTopLeftDir
# return if top left was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, top left was legal
beq 	$v0, $zero, topLeftLegalFlag
# else not legal, check top
b	isTopLegal

# flag to indicate top left was legal
topLeftLegalFlag:
li	$t0, 0

isTopLegal:
# allocate space on stack to check top
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check top
jal	checkTopDir
# return if top was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, top was legal
beq 	$v0, $zero, topLegalFlag
# else not legal, check top right
b	isTopRightLegal

# flag to indicate top was legal
topLegalFlag:
li	$t0, 0

isTopRightLegal:
# allocate space on stack to check top right
addiu	$sp, $sp, -12
# store turn owner
sw	$s5, 4($sp)
# store return address
sw	$ra, 8($sp)
# check top right
jal	checkTopRightDir
# return if top right was valid
lw	$v0, 0($sp)
# return address
lw	$ra, 8($sp)
# pop the stack
addiu	$sp, $sp, 8
# if return = 0, top right was legal
beq 	$v0, $zero, topRightLegalFlag
# else not legal, it's an illegal move
b	isMoveLegalEndPhase

# flag to indicate top right was legal
topRightLegalFlag:
li	$t0, 0

isMoveLegalEndPhase:
# when t0 = 0, then move is legal
beq 	$t0, $zero, moveIsLegal
# no moves are legal, return illegal move error
li	$v0, 1
b	moveIsIllegal

# perform the move
moveIsLegal:
li	$v0, 0

# return illegal move, jump back
moveIsIllegal:
sw	$v0, 4($sp)
jr	$ra


checkRightDir:
# load whose turn it is
lw	$t5, 4($sp)
# branch if player's turn
beq 	$t5, $zero, playersRight
# branch if pc's turn
b 	computersRight

playersRight:
# move column and row value to test
move	$t1, $s3
move	$t3, $s4
# check next column
add	$t1, $t1, 1

# allocate space on stack to check value at test position
addiu $sp, $sp, -20
# load and save current board
la	$t3, row1
sw	$t3, 0($sp)
# store row, column, and return address
sw	$t1, 4($sp)
sw	$s4, 8($sp)
sw	$ra, 16($sp)
# get value of test position
jal 	readValueAtCR
# load address
lw	$ra, 16($sp)
# load value into t2
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20
# if value is a O, branch to error
beq	$t2, 79, RightExitLoopError

# t1 holds start position flag
# t4 hold loop count
move	$t4, $t1

# exit loop if loop count (indicates range of column)
LoopRightPhaseOne:
bge	$t4, 105, rightExitLoop

# allocate space on stack to check value at test position
addiu $sp, $sp, -20
# store current board on stack
la	$t3, row1
sw	$t3, 0($sp)
# store row, column, and return address
sw	$t4, 4($sp)
sw	$s4, 8($sp)
sw	$ra, 16($sp)
# get value of test position
jal 	readValueAtCR
# load address
lw	$ra, 16($sp)
# load value into t2
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20
# if value is '-', branch to illegal
beq	$t2, 45, RightExitLoopError
# if value is 'O', branch to look at the chip
beq	$t2, 79, LoopRightPhaseTwo
# increment the column count
add	$t4, $t4, 1
# branch back to first loop
b	LoopRightPhaseOne

RightExitLoopError:
# indicate illegal move and stop looping
li	$v0, 1
b	rightExitLoop

# reverse the loop to look at selected position
LoopRightPhaseTwo:
add	$t1, $t1, -1

# indicated legal move, and look at opp. chip
LoopRightPhaseThree:
li	$v0, 0
add	$t4, $t4, -1

# allocate space on stack to set value
addiu $sp, $sp, -24
la	$t0, row1
# store position of board, column, and row
sw	$t0, 0($sp)
sw	$t4, 4($sp)
sw	$s4, 8($sp)
# store value at position
sw	$s0, 12($sp)
# store 
sw	$t3, 16($sp)
# store return adress
sw	$ra, 20($sp)
jal 	setValueAtCR
# restore board and column
lw	$t4, 4($sp)
lw	$t3, 8($sp)
lw	$ra, 20($sp)
# pop the stack
addiu	$sp, $sp, 24
# if pointing count is same as beginning position, stop loop
bne	$t4, $t1,LoopRightPhaseThree
# t1 is stop position
# t4 is flipped count
b	rightExitLoop

# check for computer
computersRight:
# move row and column value for testing
move	$t1, $s3
move	$t3, $s4
# test next column
add	$t1, $t1, 1

# allocate space on stack to check value at test position
addiu $sp, $sp, -20
# load and save board on stack
la	$t3, row1
sw	$t3, 0($sp)
# store row, column, and return address
sw	$t1, 4($sp)
sw	$s4, 8($sp)
sw	$ra, 16($sp)
# get value of test position
jal 	readValueAtCR
# load address
lw	$ra, 16($sp)
# load value into t2
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20
# if value is a X, branch to error
beq	$t2, 88, PCRightExitLoopError

# move column (loop) count for new start
move	$t4, $t1

PCLoopRightPhaseOne:
# loop if done if column count >= 105
bge	$t4, 105, rightExitLoop

# allocate space on stack to check value at test position
addiu $sp, $sp, -20
# load board and save on stack
la	$t3, row1
sw	$t3, 0($sp)
# store row, column, and return address
sw	$t4, 4($sp)
sw	$s4, 8($sp)
sw	$ra, 16($sp)
# get value of test position
jal 	readValueAtCR
# load address
lw	$ra, 16($sp)
# load value into t2
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
beq	$t2, 45, PCRightExitLoopError
# if value is 'X', valid move
beq	$t2, 88, PCLoopRightPhaseTwo

# increment column count
add	$t4, $t4, 1
# loop again
b	PCLoopRightPhaseOne

PCRightExitLoopError:
# indicate illegal move, exit loop
li	$v0, 1
b	rightExitLoop

# reverse loop and check selection position
PCLoopRightPhaseTwo:
add	$t1, $t1, -1

# indicate legal move, look at opp. piece to change
PCLoopRightPhaseThree:
li	$v0, 0
add	$t4, $t4, -1

# allocate space on stack to set value
addiu $sp, $sp, -24
la	$t0, row1
# store position of board, column, and row
sw	$t0, 0($sp)
sw	$t4, 4($sp)
sw	$s4, 8($sp)
# store addresses and values to set them
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
# restore row and column values
lw	$t4, 4($sp)
lw	$t3, 8($sp)
lw	$ra, 20($sp)
# pop the stack
addiu	$sp, $sp, 24

# once column (loop) count is same as starting position, loop is done
bne	$t4, $t1,PCLoopRightPhaseThree

# exit loop, returns
rightExitLoop:
sw	$v0, 0($sp)
jr	$ra

#############################################################

checkBotRightDir:
# load whose turn it is
lw	$s5, 4($sp)
# branch if player's turn
beq 	$s5, $zero, playersBottomRight
# branch if pc's turn
b 	PCBottomRight

playersBottomRight:
# move column and row value to test
move	$t1, $s3
move	$t3, $s4
# move over one and down one to check positio n
add	$t1, $t1, 1
add	$t3, $t3, 1

# allocate space on stack to check value at test position
addiu   $sp, $sp, -20
# load and save current board
la	$t8, row1
sw	$t8, 0($sp)
# store row, column, and return address
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
# get value of test position
jal 	readValueAtCR
lw	$ra, 16($sp)
# load value into t2
lw	$t2, 12($sp)
# pop the stack
addiu	$sp, $sp, 20
# if value is a O, branch to error
beq	$t2, 79, bottomExitLoopRightError

# t1 holds start position flag
# t4 hold loop count for column
# t5 hold loop count for row
move	$t4, $t1
move	$t5, $t3

# exit loop if loop out of range(indicates range of column/row)
bottomLoopRightPhaseOne:
bge	$t4, 105, bottomExitLoopRight
bge	$t5, 9, bottomExitLoopRight

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store starting row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', branch to look at the chip
# increment the column count
# increment the row count
# branch back to first loop
beq	$t2, 45, bottomExitLoopRightError
beq	$t2, 79, bottomLoopRightPhaseTwo
add	$t4, $t4, 1
add	$t5, $t5, 1
b	bottomLoopRightPhaseOne

# indicate illegal move and stop looping
bottomExitLoopRightError:
li	$v0, 1
b	bottomExitLoopRight

# reverse the loop to look at selected position
bottomLoopRightPhaseTwo:
add	$t1, $t1, -1
add	$t3, $t3, -1

# indicated legal move, and look at opp. chip
bottomLoopRightPhaseThree:
li	$v0, 0
add	$t4, $t4, -1
add	$t5, $t5, -1

# allocate space on stack to set value
# store address of board
# store position of board, column, and row
# store value at position
# store return address
# restore board, column, and row
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
bne	$t4, $t1, bottomLoopRightPhaseThree
# t1 is stop position
# t4 is flipped count
b	bottomExitLoopRight

# check for computer
# move row and column value for testing
# test next column and down a row
PCBottomRight:
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 1
add	$t3, $t3, 1

# allocate space on stack to check value at test position
# store current board on stack
# store stopping row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20
# if value is a X, branch to error
beq	$t2, 88, PCbottomExitLoopRightError

# move column (loop) count for new start
# move row (loop) count for new start
move	$t4, $t1
move	$t5, $t3

# exit loop if loop out of range(indicates range of column/row)
PCBottomLoopPhaseOne:
bge	$t4, 105, bottomExitLoopRight
bge	$t5, 9, bottomExitLoopRight

addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCbottomExitLoopRightError
beq	$t2, 88, PCBottomLoopPhaseTwo
add	$t4, $t4, 1
add	$t5, $t5, 1
b	PCBottomLoopPhaseOne
PCbottomExitLoopRightError:
# indicate illegal move and stop looping
li	$v0, 1
b	bottomExitLoopRight

# reverse the loop to look at selected position
PCBottomLoopPhaseTwo:
add	$t1, $t1, -1
add	$t3, $t3, -1

# indicate legal move, look at opp. piece to change
PCBottomLoopPhaseThree:
li	$v0, 0
add	$t4, $t4, -1
add	$t5, $t5, -1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t4, $t1,PCBottomLoopPhaseThree

bottomExitLoopRight:

sw	$v0, 0($sp)
jr	$ra
#end checkBotRightDir function


#########################################################################


checkBottomDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, playersBottom
b 	pcBottom

playersBottom:
move	$t1, $s3
move	$t3, $s4
add	$t3, $t3, 1

#-------------------------------------------------------------------------#
# Check each position in clockwise direction of the selected piece        #
# Therefore, we will do a do - while loop to check each surrounding piece #
# of the selected piece to ensure legality of the move.                   #
#-------------------------------------------------------------------------#

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop counts to indicate
# new starting position
beq	$t2, 79, BottomExitLoopError
move	$t4, $t1
move	$t5, $t3

# is done if hit max range for column and row
loopBottomPhaseOne:
bge	$t4, 105, ExitLoopBottom
bge	$t5, 9, ExitLoopBottom

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, BottomExitLoopError
beq	$t2, 79, loopBottomPhaseTwo
add	$t4, $t4, 0
add	$t5, $t5, 1
b	loopBottomPhaseOne

# indicate illegal move, exit loop
BottomExitLoopError:
li	$v0, 1
b	ExitLoopBottom

# reverse loop and check selection position
loopBottomPhaseTwo:
add	$t1, $t1, 0
add	$t3, $t3, -1

# indicate legal move, look at opp. piece to change
loopBottomPhaseThree:
li	$v0, 0
add	$t4, $t4, 0
add	$t5, $t5, -1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once row (loop) count is same as starting position, loop is done
bne	$t3, $t5, loopBottomPhaseThree
b	ExitLoopBottom

pcBottom:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 0
add	$t3, $t3, 1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X, branch to error
# move row/column counts for new start
beq	$t2, 88, PCBottomExitLoopError
move	$t4, $t1
move	$t5, $t3

# is done if hit max range for column and row
PCloopBottomPhaseOne:
bge	$t4, 105, ExitLoopBottom
bge	$t5, 9, ExitLoopBottom

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCBottomExitLoopError
beq	$t2, 88, PCloopBottomPhaseTwo
add	$t4, $t4, 0
add	$t5, $t5, 1
b	PCloopBottomPhaseOne

PCBottomExitLoopError:
# indicate illegal move and stop looping
li	$v0, 1
b	ExitLoopBottom

# reverse the loop to look at selected position
PCloopBottomPhaseTwo:
add	$t1, $t1, 0
add	$t3, $t3, -1

# indicate legal move, look at opp. piece to change
PCloopBottomPhaseThree:
li	$v0, 0
add	$t4, $t4, 0
add	$t5, $t5, -1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once loop count is same as starting position, loop is done
# so exit loop and returns
# else, keep looping!
bne	$t3, $t5,PCloopBottomPhaseThree

ExitLoopBottom:

sw	$v0, 0($sp)
jr	$ra
#end checkBottomDir function


########################################################################


checkBotLeftDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, playersbotleft
b 	pcbotleft

playersbotleft:
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, 1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop count to t1 and t3 to indicate
# new starting position
beq	$t2, 79, BottomLoopLeftError

move	$t4, $t1
move	$t5, $t3
BottomLoopLeftPhaseOne:
ble	$t4, 96, bottomLoopLeftExit
bge	$t5, 9, bottomLoopLeftExit

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, BottomLoopLeftError
beq	$t2, 79, BottomLoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, 1
b	BottomLoopLeftPhaseOne
BottomLoopLeftError:
# indicate illegal move and stop looping
li	$v0, 1
b	bottomLoopLeftExit

# reverse the loop to look at selected position
BottomLoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, -1

# indicate legal move, look at opp. piece to change
BottomLoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, -1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t3, $t5, BottomLoopLeftPhaseThree
b	bottomLoopLeftExit

pcbotleft:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, 1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X, branch to error
# move row/column counts for new start
beq	$t2, 88, PCBottomLoopLeftError
move	$t4, $t1
move	$t5, $t3

PCBottomLoopLeftPhaseOne:
ble	$t4, 96, bottomLoopLeftExit
bge	$t5, 9, bottomLoopLeftExit

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCBottomLoopLeftError
beq	$t2, 88, PCBottomLoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, 1
b	PCBottomLoopLeftPhaseOne
PCBottomLoopLeftError:
# indicate illegal move and stop looping
li	$v0, 1
b	bottomLoopLeftExit

# reverse the loop to look at selected position
PCBottomLoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, -1

# indicate legal move, look at opp. piece to change
PCBottomLoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, -1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once loop count is same as starting position, loop is done
# so exit loop and returns
# else, keep looping!
bne	$t3, $t5,PCBottomLoopLeftPhaseThree
bottomLoopLeftExit:
sw	$v0, 0($sp)
jr	$ra
#end checkBottomDir function


########################################################################

checkLeftDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, playersLeft
b 	PCLeft
playersLeft:
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, 0

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop count to t1 and t3 to indicate
# new starting position
beq	$t2, 79, ExitErrorLoopLeft

move	$t4, $t1
move	$t5, $t3
LoopLeftPhaseOne:
ble	$t4, 96, leftLoopExit
ble	$t5, 0, leftLoopExit

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, ExitErrorLoopLeft
beq	$t2, 79, LoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, 0
b	LoopLeftPhaseOne
ExitErrorLoopLeft:
# indicate illegal move and stop looping
li	$v0, 1
b	leftLoopExit

# reverse the loop to look at selected position
LoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, 0

# indicate legal move, look at opp. piece to change
LoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, 0

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t1, $t4, LoopLeftPhaseThree
b	leftLoopExit

PCLeft:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, 0

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X, branch to error
# move row/column counts for new start
beq	$t2, 88, PCExitErrorLoopLeft
move	$t4, $t1
move	$t5, $t3

PCLoopLeftPhaseOne:
ble	$t4, 96, leftLoopExit
ble	$t5, 0, leftLoopExit

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCExitErrorLoopLeft
beq	$t2, 88, PCLoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, 0
b	PCLoopLeftPhaseOne
PCExitErrorLoopLeft:
# indicate illegal move and stop looping
li	$v0, 1
b	leftLoopExit

# reverse the loop to look at selected position
PCLoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, 0

# indicate legal move, look at opp. piece to change
PCLoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, 0

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once loop count is same as starting position, loop is done
# so exit loop and returns
# else, keep looping!
bne	$t1, $t4,PCLoopLeftPhaseThree

leftLoopExit:

sw	$v0, 0($sp)
jr	$ra
#end checkLeftDir function


########################################################################


checkTopLeftDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, TopPlayersLeft
b 	TopPCLeft
TopPlayersLeft:
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop count to t1 and t3 to indicate
# new starting position
beq	$t2, 79, TopExitErrorLoopLeft

move	$t4, $t1
move	$t5, $t3
TopLoopLeftPhaseOne:
ble	$t4, 96, topExitLoopLeft
ble	$t5, 0, topExitLoopLeft

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, TopExitErrorLoopLeft
beq	$t2, 79, TopLoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, -1
b	TopLoopLeftPhaseOne
TopExitErrorLoopLeft:
# indicate illegal move and stop looping
li	$v0, 1
b	topExitLoopLeft

# reverse the loop to look at selected position
TopLoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
TopLoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t3, $t5, TopLoopLeftPhaseThree
b	topExitLoopLeft

TopPCLeft:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, -1
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X, branch to error
# move row/column counts for new start
beq	$t2, 88, PCTopExitErrorLoopLeft
move	$t4, $t1
move	$t5, $t3

PCTopLoopLeftPhaseOne:
ble	$t4, 96, topExitLoopLeft
ble	$t5, 0, topExitLoopLeft

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCTopExitErrorLoopLeft
beq	$t2, 88, PCTopLoopLeftPhaseTwo
add	$t4, $t4, -1
add	$t5, $t5, -1
b	PCTopLoopLeftPhaseOne
PCTopExitErrorLoopLeft:
# indicate illegal move and stop looping
li	$v0, 1
b	topExitLoopLeft

# reverse the loop to look at selected position
PCTopLoopLeftPhaseTwo:
add	$t1, $t1, 1
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
PCTopLoopLeftPhaseThree:
li	$v0, 0
add	$t4, $t4, 1
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once loop count is same as starting position, loop is done
# so exit loop and returns
# else, keep looping!
bne	$t3, $t5,PCTopLoopLeftPhaseThree

topExitLoopLeft:

sw	$v0, 0($sp)
jr	$ra
#end checkTopLeftDir function


########################################################################

checkTopDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, topPlayers
b 	topPCC
topPlayers:
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 0
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop count to t1 and t3 to indicate
# new starting position
beq	$t2, 79, topExitErrorLoop

move	$t4, $t1
move	$t5, $t3
LoopTopPhaseOne:
ble	$t4, 96, exitTopLoop
ble	$t5, 0, exitTopLoop

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, topExitErrorLoop
beq	$t2, 79, LoopTopPhaseTwo
add	$t4, $t4, 0
add	$t5, $t5, -1
b	LoopTopPhaseOne
topExitErrorLoop:
# indicate illegal move and stop looping
li	$v0, 1
b	exitTopLoop

# reverse the loop to look at selected position
LoopTopPhaseTwo:
add	$t1, $t1, 0
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
LoopTopPhaseThree:
li	$v0, 0
add	$t4, $t4, 0
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t3, $t5, LoopTopPhaseThree
b	exitTopLoop

topPCC:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 0
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X, branch to error
# move row/column counts for new start
beq	$t2, 88, PCTopExitErrorLoop
move	$t4, $t1
move	$t5, $t3

PCLoopTopPhaseOne:
ble	$t4, 96, exitTopLoop
ble	$t5, 0, exitTopLoop

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCTopExitErrorLoop
beq	$t2, 88, PCLoopTopPhaseTwo
add	$t4, $t4, 0
add	$t5, $t5, -1
b	PCLoopTopPhaseOne
PCTopExitErrorLoop:
# indicate illegal move and stop looping
li	$v0, 1
b	exitTopLoop

# reverse the loop to look at selected position
PCLoopTopPhaseTwo:
add	$t1, $t1, 0
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
PCLoopTopPhaseThree:
li	$v0, 0
add	$t4, $t4, 0
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# once loop count is same as starting position, loop is done
# so exit loop and returns
# else, keep looping!
bne	$t3, $t5,PCLoopTopPhaseThree

exitTopLoop:

sw	$v0, 0($sp)
jr	$ra
#end checkTopLeftDir function


########################################################################

checkTopRightDir:
# load whose turn it is
# branch if player's turn
# branch if pc's turn
# move column and row value to test
# change value of row and column to corresponding direction
lw	$s5, 4($sp)
beq 	$s5, $zero, TopPlayersRight
b 	TopPCRight
TopPlayersRight:
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 1
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu   $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a O, branch to error
# else, move the current column/row
# loop count to t1 and t3 to indicate
# new starting position
beq	$t2, 79, TopExitLoopErrorRight

move	$t4, $t1
move	$t5, $t3

# is done if hit max range for column and row
TopLoopRightPhaseOne:
bge	$t4, 105, exitTopLoopRight
ble	$t5, 0, exitTopLoopRight

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'O', valid move
# increment counts to continue loop
beq	$t2, 45, TopExitLoopErrorRight
beq	$t2, 79, TopLoopRightPhaseTwo
add	$t4, $t4, 1
add	$t5, $t5, -1
b	TopLoopRightPhaseOne
TopExitLoopErrorRight:
# indicate illegal move and stop looping
li	$v0, 1
b	exitTopLoopRight

# reverse the loop to look at selected position
TopLoopRightPhaseTwo:
add	$t1, $t1, -1
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
TopLoopRightPhaseThree:
li	$v0, 0
add	$t4, $t4, -1
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s0, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)

jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 20($sp)
addiu	$sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t3, $t5, TopLoopRightPhaseThree
b	exitTopLoopRight

TopPCRight:
# check for computer
# move row and column value for testing
# increment values to test
move	$t1, $s3
move	$t3, $s4
add	$t1, $t1, 1
add	$t3, $t3, -1

# do part of a do-while
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t1, 4($sp)
sw	$t3, 8($sp)
sw	$ra, 16($sp)
jal 	readValueAtCR
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is a X or '-', branch to error
# move row/column counts for new start
beq	$t2, 88, PCTopExitLoopErrorRight
beq	$t2, 45, PCTopExitLoopErrorRight

move	$t4, $t1
move	$t5, $t3

# is done if hit max range for column and row
PCTopLoopRightPhaseOne:
bge	$t4, 105, exitTopLoopRight
ble	$t5, 0, exitTopLoopRight

# loop part to test each surrounding position value
# allocate space on stack to check value at test position
# store current board on stack
# store the test (checking process) row, column, and return address
# get value of test position
# load address
# load value into t2
# pop the stack
addiu $sp, $sp, -20
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$ra, 16($sp)

jal 	readValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw	$ra, 16($sp)
lw	$t2, 12($sp)
addiu	$sp, $sp, 20

# if value is '-', branch to illegal
# if value is 'X', valid move
# increment counts to continue loop
beq	$t2, 45, PCTopExitLoopErrorRight
beq	$t2, 88, PCTopLoopRightPhaseTwo
add	$t4, $t4, 1
add	$t5, $t5, -1
b	PCTopLoopRightPhaseOne
PCTopExitLoopErrorRight:
# indicate illegal move and stop looping
li	$v0, 1		#illegal move
b	exitTopLoop

# reverse the loop to look at selected position
PCTopLoopRightPhaseTwo:
add	$t1, $t1, -1
add	$t3, $t3, 1

# indicate legal move, look at opp. piece to change
PCTopLoopRightPhaseThree:
li	$v0, 0
add	$t4, $t4, -1
add	$t5, $t5, 1

# allocate space on stack to set value
# store position of board, column, and row
# store addresses and values to set them
# restore row and column values
# pop the stack
addiu $sp, $sp, -24
la	$t8, row1
sw	$t8, 0($sp)
sw	$t4, 4($sp)
sw	$t5, 8($sp)
sw	$s1, 12($sp)
sw	$t3, 16($sp)
sw	$ra, 20($sp)
jal 	setValueAtCR
lw	$t4, 4($sp)
lw	$t5, 8($sp)
lw $ra, 20($sp)
addiu $sp, $sp, 24

# if pointing count is same as beginning position, stop loop
# else keep looping!
bne	$t3, $t5, PCTopLoopRightPhaseThree

exitTopLoopRight:

sw	$v0, 0($sp)
jr	$ra
#end checkTopRightDir function


########################################################################
# Mips Display Drawing functions
########################################################################
#	Draw Board Function	#
#################################
drawBoard:
addiu	$sp,	$sp,	-28	#allocate 7 words to stack
sw	$t0,	0($sp)		#save $t0 value
sw	$t1,	4($sp)		#save $t1 value
sw	$t3,	8($sp)		#save $t3 value
sw	$t4,	12($sp)		#save $t4 value
sw	$t5,	16($sp)		#save $t5 value
sw	$t9,	20($sp)		#save $t9 value
sw	$ra,	24($sp)		#save return address
add	$t0,	$zero,	$gp		#set to start of $gp
ld	$t1,	colorLightGreen		#set some initial values
ld	$t3,	colorDarkGreen
move	$t4,	$zero			#running total value for columns
move	$t5,	$zero			#running total value for rows
li	$t9,	4			#termination value for loops
drawBoardRowLoop:
beq	$t5,	$t9,	drawBoardExit
move	$t4,	$zero			#reset running total value
jal	drawBoardLoop
move	$t4,	$zero			#reset running total value
jal	drawBoardLoop1
addi	$t5,	$t5,	1		#increment running total for rows
j	drawBoardRowLoop		#loop again
drawBoardLoop:
beq	$t4,	$t9,	drawBoardLoopExit	#if running total == 8, break loop
sw 	$t1,	($t0)		#color pixel lightGreen
addi	$t0,	$t0,	4	#increment frameBuffer to next pixel/box
sw	$t3,	($t0)		#color pixel darkGreen
addi	$t0,	$t0,	4	#increment frameBuffer to next pixel/box
addi	$t4,	$t4,	1	#increment running total
j	drawBoardLoop		#loop again
drawBoardLoopExit:
jr	$ra
drawBoardLoop1:
beq	$t4,	$t9,	drawBoardLoop1Exit	#if running total == 8, break loop
sw 	$t3,	($t0)		#color pixel lightGreen
addi	$t0,	$t0,	4	#increment frameBuffer to next pixel/box
sw	$t1,	($t0)		#color pixel darkGreen
addi	$t0,	$t0,	4	#increment frameBuffer to next pixel/box
addi	$t4,	$t4,	1	#increment running total
j	drawBoardLoop1		#loop again
drawBoardLoop1Exit:
jr $ra
drawBoardExit:
lw	$ra,	24($sp)		#restore $ra value
lw	$t9,	20($sp)		#restore $t9 value
lw	$t5,	16($sp)		#restore $t5 value
lw	$t4,	12($sp)		#restore $t4 value
lw	$t3,	8($sp)		#restore $t3 value
lw	$t1,	4($sp)		#restore $t1 value
lw	$t0,	0($sp)		#restore $t0 value
addiu	$sp,	$sp,	28	#release 7 words to stack
jr	$ra

#################################################
#	Draw Start Postitions Function		#
#################################################
drawStartPositions:
addiu	$sp,	$sp,	-12	#allocate 3 words to stack
sw	$t0,	0($sp)		#save $t0 value
sw	$t1,	4($sp)		#save $t1 value
sw	$t3,	8($sp)		#save $t3 value
add	$t0,	$zero,	$gp	#set to start of $gp
ld	$t1,	colorWhite	#set some initial values
ld	$t3,	colorBlack
addi	$t0,	$t0,	108	#move frameBuffer to middle top left square
sw	$t1,	($t0)		#color pixel white
addi	$t0,	$t0,	4	#move frameBuffer to middle top right square
sw	$t3,	($t0)		#color pixel black
addi	$t0,	$t0,	28	#move frameeBuffer to middle bottom left square
sw	$t3,	($t0)		#color pixel black
addi	$t0,	$t0,	4	#move frameBuffer to middle bottom right square
sw	$t1,	($t0)		#color pixel white
lw	$t3,	8($sp)		#restore $t3 value
lw	$t1,	4($sp)		#restore $t1 value
lw	$t0,	0($sp)		#restore $t0 value
addiu	$sp,	$sp,	12	#release 3 words from stack
jr	$ra

#################################
#	Draw Square Function	#
#######################################################################
#$a0 contains col num, $a1 contains row number, $a2 char to change to #
#######################################################################
drawSquare:
addiu	$sp,	$sp,	-12	#allocate 3 words to stack
sw	$t0,	0($sp)		#save $t0 value
sw	$t1,	4($sp)		#save $t1 value
sw	$ra,	8($sp)		#save $ra value
lw	$t0,	colorWhite	#set some initial values
lw	$t1,	colorBlack
subiu	$a1,	$a1,	1	#to account for index counting
subiu	$a0,	$a0,	1	#to account for index counting
sll	$a1,	$a1,	5	#to get row offset, mul by 32 (8 units per row, 4 address each)
sll	$a0,	$a0,	2	#to get col offset(address 4 each)
addu	$a0,	$a0,	$a1	#to get total offset
addu	$a0,	$a0,	$gp	#to get the address needed in the $gp area
beq	$a2,	79,	drawBlack	#if argument is 0, draw black
sw	$t0,	($a0)			#otherwise draw white
j 	drawSquareExit
	drawBlack:
	sw	$t1,	($a0)		#draw black
	j	drawSquareExit
drawSquareExit:
lw	$t0,	0($sp)		#restore $t0 value
lw	$t1,	4($sp)		#restore $t3 value
lw	$ra,	8($sp)		#restore $ra value
addiu	$sp,	$sp,	12	#release 3 words from stack
jr $ra

#############################
#sounds
######################################################################################
# $a0 controls pitch, $a1 sound duration in ms, $a2 which instrument used, $a3 volume
######################################################################################
error_sound:
li $a3, 120
la $v0, 33
li $a2, 5

li $a0, 63
li $a1, 100
syscall

li $a0, 60
li $a1, 100
syscall

jr $ra

ding_sound:
li $a3, 120
la $v0, 33
li $a2, 4

li $a0, 63
li $a1, 300
syscall

jr $ra

victory_sound:
li $a3, 120
la $v0, 33
li $a2, 5

li $a0, 64
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 65
li $a1, 250
syscall
li $a0, 67
li $a1, 250
syscall
li $a0, 67
li $a1, 250
syscall
li $a0, 65
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 62
li $a1, 250
syscall
li $a0, 60
li $a1, 250
syscall
li $a0, 60
li $a1, 250
syscall
li $a0, 62
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 64
li $a1, 375
syscall
li $a0, 62
li $a1, 125
syscall
li $a0, 62
li $a1, 500
syscall
#second bar

li $a0, 64
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 65
li $a1, 250
syscall
li $a0, 67
li $a1, 250
syscall
li $a0, 67
li $a1, 250
syscall
li $a0, 65
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 62
li $a1, 250
syscall
li $a0, 60
li $a1, 250
syscall
li $a0, 60
li $a1, 250
syscall
li $a0, 62
li $a1, 250
syscall
li $a0, 64
li $a1, 250
syscall
li $a0, 62
li $a1, 375
syscall
li $a0, 60
li $a1, 125
syscall
li $a0, 60
li $a1, 500
syscall

jr $ra
