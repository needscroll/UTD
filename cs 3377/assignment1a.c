/*
james wei
jaw 160230
cs 3377.0W3
Dr. Kapoor
15 January 2018
assignment 1
*/

#include <stdio.h>

void main()
{
  int min = 100;
  int max = 400;
  double celsius;
  double kelvin;
  const double c_to_k = 273.15;
  int counter = min;

  printf("%-13s" ,"fahrenheit");
  printf("%-13s" ,"celsius");
  printf("%-13s" ,"kelvin\n");
  printf("\n");

  while (counter <= max)
  {
    celsius = (5.0/9) * (counter - 32);
    kelvin = (5.0/9) * (counter - 32) + c_to_k;

    printf("%-13d", counter);
    printf("%-13.2f", celsius);
    printf("%-13.2f", kelvin);
    printf("\n");
    counter++;
  }
}
