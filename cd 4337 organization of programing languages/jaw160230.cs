using System;

class MainClass {
  public static void Main (string[] args) {
    EvenOdd(5);
    EvenOdd(10);
    EvenOdd(255);
    EvenOdd(1250);
    EvenOdd(0);
    Console.WriteLine("");
    
    int[] arr1={-3,8,7,6,5,-4,3,2,1};
    SortMyArray(arr1); 
    int[] test_array={1,2,3,4,5};
    SortMyArray(test_array); 
    int[] test_array1 = {};
    SortMyArray(test_array1);
    int[] test_array2={1,-1,-2,2,3,-3,-4,4,5,-5,0};
    SortMyArray(test_array2); 
    int[] test_array3={1, 1, 1, 100, 75, 2, 2, 37, 55, 55};
    SortMyArray(test_array3); 
    Console.WriteLine("");
    
    Console.WriteLine(myFib(5));
    Console.WriteLine(myFib(10));
    Console.WriteLine(myFib(8));
    //Console.WriteLine(myFib(45));
    Console.WriteLine(myFib(0));
    Console.WriteLine("");
    
    Console.WriteLine(MyMathFunction(10, 10, Multiply));
    Console.WriteLine(MyMathFunction(50, 10, Divide));
    Console.WriteLine(MyMathFunction(10, 0, Divide));
    Console.WriteLine(MyMathFunction(20, 25, Add));
    Console.WriteLine(MyMathFunction(25, 20, Subtract));
  }
  
  public static void EvenOdd(int x)
  {
    if (x == 0)
    {
      Console.WriteLine("Even");
      return;
    }
    if (x == 1)
    {
      Console.WriteLine("Odd");
      return;
    }
    if (x > 1)
    {
      EvenOdd(x - 2);
    }
    else
    {
      EvenOdd(x + 2);
    }
  }
  
  public static void SortMyArray(int[] arr)
  {
    if (arr.Length == 0)
    {
      Console.WriteLine("can not sort empty array");
    }
    
    for (int i = 0; i < arr.Length; i++)
    {
      for (int j = 0; j < arr.Length - 1; j++)
      {
        if (arr[j] > arr[j + 1])
        {
        int holder = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = holder;
        }
      }
    }
    
    for (int i = 0; i < arr.Length; i++)
    {
      Console.WriteLine(arr[i]);
    }
    Console.WriteLine("");
  }
  
  public static int myFib(int x)
  {
    if (x == 0)
    {
      return 1;
    }
    
    if (x == 1)
    {
      return 1;
    }
    return myFib(x - 1) + myFib(x - 2);
  }

  private static string MyMathFunction(int v1, int v2, Func<int, int, string> method)
  {
    return method(v1, v2).ToString();
  }
   
  private static string Multiply(int v1, int v2)
  {
    return (v1 * v2).ToString();
  }
  private static string Divide(int v1, int v2)
  {
    if (v2 == 0)
    {
      return "can not divide by 0";
    }
    return (v1 / v2).ToString();
  }
  private static string Add(int v1, int v2)
  {
  return (v1 + v2).ToString();
  }
  private static string Subtract(int v1, int v2)
  {
  return (v1 - v2).ToString();
  }

}
