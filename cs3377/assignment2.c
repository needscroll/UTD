/*
james wei
jaw 160230
cs 3377.0W3
Dr. Kapoor
23 January 2018
assignment 2
*/
#include <stdio.h>
#include <stdbool.h>

void main()
{
  int total_num = 0;
  int total_commented = 0;
  int total_commented_words = 0;
  char input;
  char holder[2];
  char *file;
  bool in_comment1 = false;
  bool in_comment2 = false;

  while ((input = getchar()) != EOF)
  {
    holder[0] = holder[1];
    holder[1] = input;

    if (in_comment1 || in_comment2)
    {
      if (holder[1] == '\n')
      {
        in_comment1 = false;
      }
      else if (holder[0] == '*' && holder[1] == '/')
      {
        in_comment2 = false;
        total_commented--;
      }
      else
      {
        total_commented++;
      }
      if (holder[1] == ' ' || holder[1] == '\n' || holder[1] == '\t')
      {
        total_commented_words++;
      }
    }
    if (holder[0] == '/' && holder[1] == '/')
    {
      in_comment1 = true;
    }
    if (holder[0] == '/' && holder[1] == '*')
    {
      in_comment2 = true;
      total_commented_words++;
    }

    //putchar('\n');
    total_num++;
  }

  printf("the total number of overall characters is ");
  printf("%d", total_num);
  printf("\n");
  printf("The total number of commented characters is ");
  printf("%d", total_commented);
  printf("\n");
  printf("The total number of commented words is ");
  printf("%d", total_commented_words);
  printf("\n");
}
