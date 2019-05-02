
import random
def rand(a, b):
    return random.randint(a, b)

rand_num = rand(-100, 100)
print(rand_num)

guess = 200
guess_num = 0
guess_left = 7

name = raw_input("enter your name\n")

while rand_num != guess and guess_left > 0:
    print "enter a number. you have ", guess_left, "guesses left"
    guess = input()
    guess_num += 1
    guess_left -= 1
    if guess > rand_num:
        print("guess was higher than number")
    elif guess < rand_num:
        print("guess was lower than number")
    else:
        print("you guessed the number correctly")

print "your total score was", guess_num
if guess_left == 0 and guess != rand_num:
    print "you did not guess the number in time"
file = open('report.txt', 'w')
file.write(name)
file.write(":")
file.write('%1d\n' % guess_num)
file.write("\n")
