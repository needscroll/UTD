#!/usr/bin/perl
# Strict and warnings are recommended.
use strict;
use warnings;

my $name;
my $guess = 0;
my $limit = 7;
my $input = 0;
my $max = 100;
my $rand = int(rand($max) + 100);

#print $rand;
print "enter your name: ";
$name  = <STDIN>;
while ($input != $rand && $guess < $limit)
{
  $guess++;
  print "guess a number between 100 and 200. you have ", $limit - $guess + 1, " guesses left\n";
  $input = <STDIN>;

  if ($input < $rand)
  {
    print "your guess was lower than the number\n";
  }
  if ($input > $rand)
  {
    print "your guess was higher than the number\n";
  }
}
if ($input == $rand)
{
  print "you guessed the number correctly\n";
  print "the number of guesses you used is ", $guess, " \n";
}
else
{
  print "you have run out of guesses\n";
}

open(my $file, '>>', 'output.txt');
print $file "name: ", $name, "score: ", $guess, "\n--------\n";
