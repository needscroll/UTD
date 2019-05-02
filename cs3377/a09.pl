#!/usr/bin/perl
# Strict and warnings are recommended.
use strict;
use warnings;

my $x = 100;
print $x * 2 + 5;
print "\n";


my $max = 20;
my $rand1 = int(rand($max) - 10);
my $rand2 = int(rand($max) - 10);
print "first random number is $rand1\n";
print "second random number is $rand2\n";

if ($rand1 == $rand2)
{
  print "the numbers are the same\n";
}
else
{
  print "the numbers are not the same\n";
}

random_sum();

my $a = 2;
my $b = 3;
my $sum1 = $a * $a + $b * $b;
print "the sum of the squares of 2 and 3 are ";
print "$sum1\n";

my @x = (1, 2, 3);
my @y = (4, 5, 6);
my @z;
push (@x, @y);
print "The first list: ", @x[0, 1, 2], "\n";
print "The second list: ", @x[3, 4, 5], "\n";
print "The middle elements of the resulting combined list: ", @x[1, 2, 3, 4], "\n";

print "enter input 1: ";
my $input1 = <STDIN>;
print "enter input 2: ";
my $input2 = <STDIN>;
print "enter input 3: ";
my $input3 = <STDIN>;


if ($input1 eq $input2)
{
  print "input1 matches input2\n";
}
elsif ($input1 eq $input3)
{
  print "input 1 matches input 3\n";
}
elsif ($input2 eq $input3)
{
  print "input 2 matches input 3\n";
}
else
{
  print "none of the inputs match\n";
}

my $counter = 0;
my @arr = ();
while ($counter < 100)
{
  push(@arr, rand(100));
  $counter++;
}
$counter = 0;
open(my $file, '>>', 'output.txt');
while ($counter < 100)
{
  print $file int(@arr[$counter]), "\n";
  $counter++;
}

sub random_sum
{
  my $sum = $rand1 * $rand1 + $rand2 * $rand2;
  print "the sum of the squares of the two random numbers is ";
  print "$sum\n";
}
