function EvenOdd(x) {x};
function EvenOdd(x) {
  if (x === parseInt(x))
  {
    if (x == 0)
    {
      console.log("even");
    }
    else if (x == 1)
    {
      console.log("odd");
    }
    if (x >= 2)
    {
       EvenOdd(x - 2); 
    }
  }
  else if (x !== parseInt(x))
  {
    console.log("not valid nuber");
  }
}

var Add = 1;
var Subtract = 2;
var Multiply = 3;
var Divide = 4;

function MyMathFunction(x, y, op)
{
  if (op == 1)
  {
    console.log(add(x, y));
  }
  if (op == 2)
  {
    console.log(subtract(x, y));
  }
  if (op == 3)
  {
    console.log(multiply(x, y));
  }
  if (op == 4)
  {
    console.log(divide(x, y));
  }
}

function add(x, y)
{
  return x + y;
}

function subtract(x, y)
{
  return x - y;
}

function multiply(x, y)
{
  return x * y;
}

function divide(x, y)
{
  if (y == 0)
  {
    return "can not divide by 0"
  }
  return x / y;
}

function SortMyArray(arrayP)
{
  if (arrayP.length === 0)
  {
    console.log("can not sort empty array");
  }
  
  for (var counter = 0; counter < arrayP.length; counter++)
  {
    for(var i = 0; i < arrayP.length - 1; i++){
      if (arrayP[i] > arrayP[i+1])
      {
        var holder = arrayP[i];
        arrayP[i] = arrayP[i+1];
        arrayP[i+1] = holder;
      }
   }
  }
  for(var i = 0; i < arrayP.length; i++){
      console.log(arrayP[i]);
   }
}

function myFib(x) {return x};
function myFib(x)
{
  if (x == 0)
  {
    return 1;
  }
  if (x == 1)
  {
    return 1;
  }
  if (x > 1 && x === parseInt(x))
  {
    return myFib(x - 1) + myFib(x - 2);
  }
  else
  {
    console.log("not an integer value");
  }
}