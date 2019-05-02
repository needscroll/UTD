#!/bin/bash

echo "enter a number"
read num1
echo "enter another number"
read num2

echo "addition (x + y)"
sum=$(($num1 + $num2))
echo "$sum"
echo "subtraction (x - y)"
sub=$(($num1 - $num2))
echo "$sub"
echo "multiplication (x*y)"
mult=$(($num1*$num2))
echo "$mult"
echo "division (x / y)"
div=$(($num1/$num2))
echo "$div"
