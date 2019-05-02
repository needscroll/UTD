#!/bin/bash



game()
{
  let "limit=129"

  let "guess=-1"

  let "random_num = ($RANDOM % $limit)"
  echo $random_num
  echo "enter your name"
  read name


  while [ "$guess" -ne "$random_num" ]
  do
    echo "guess a number between 0 and 128"
    read guess

    if [[ "$guess" -lt "$random_num" ]]; then
      echo "low guess"
    elif [[ "$guess" -gt "$random_num" ]]; then
      echo "high guess"
    fi

    counter=$[$counter+1]
  done
}

let "counter=0"
let "name=1"
game
echo "$name $counter"
echo "$name $counter" >> output.txt
