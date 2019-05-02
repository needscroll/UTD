#!/bin/bash

random_xy()
{
  let "first = ($RANDOM % 10) + 1"
  let "second = ($RANDOM % 10) + 1"

  echo "first number is $first"
  echo "second number is $second"


  if [ $first -ne $second ]; then
    echo "they are not the same numbers"
  else
    echo "they are the same numbers"
  fi
}


match()
{
  let "args = $#"
  if [ $args -ne 1 ]; then
    echo "no args entered"
  else
      let "num = ($RANDOM % 10) + 1"
      if [ $num -eq $1 ]; then
      echo "same number"
    else
      echo "not same"
    fi
  fi

}

test_arg()
{
  let "args1 = $#"
  if [ $args1 -eq 1 ]; then
    if [ -f $1 ]; then
      echo "is a file"
    else
      echo "is not file"
    fi
  else
    echo "no arguments entered"
fi
}

use_case()
{
  echo "is the sky blue?"
  read input
  inputl=${input,,}

  case $inputl in
    "y") echo "Agreed";;
    "n") echo "Disagreed";;
    "yes") echo "Agreed";;
    "no") echo "Disagreed";;
    *) echo "invalid input";;
  esac
}

3matching()
{
  echo "enter 3 words"
  read input1
  read input2
  read input3

  if [ "$input1" == "$input2" ]; then
    echo "input1 matches input2"
  elif [ "$input1" == "$input3" ]; then
    echo "input1 matches input3"
  elif [ "$input2" == "$input3" ]; then
    echo "input2 matches input3"
  else
    echo "none of the inputs match"
  fi
}

files()
{
  for f in */; do
    if [ -d $f ]; then
      echo "$f"
    fi
  done
}

looping()
{
  echo "enter a filename"
  read filename
  counter=1
  while [ $counter -le 99 ]
  do
  echo $counter >> "$filename"
  counter=$[$counter+1]
done
}

looping2()
{
  echo "enter a filename"
  read filename
  counter=1
  until [ $counter -eq 100 ]
  do
  echo $counter >> "$filename"
  counter=$[$counter+1]
done
}

random_xy
match $1
test_arg $1
use_case
3matching
files
looping
looping2
