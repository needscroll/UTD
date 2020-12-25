Name: James Wei
NetID: jaw160230
Class: CS 6332.001
Assignment 0x1

part 1:
simple compilation of source files into object files with
gcc -c driver1.c memsets.c

linked object files into executable
gcc driver1.o memsets.o -o part1

part 2:
compiled source files into object files with
gcc -c driver1.c memsets.c

packaged memset object file into library
ar cr libmemsets.a memsets.o

linked driver object file with library to form executable
gcc driver1.o libmemsets.a -o part2

part 3:
compiled source files into object files for use in library
gcc -fPIC -c driver1.c
gcc -fPIC -c memsets.c

created dynamic library from memsets object
gcc -shared -o libmemsets.so memsets.o

create executable by linking library
gcc -L$(PWD) -Wall -o part3 driver1.c -lmemsets

export LD_LIBRARY_PATH to current directory and run executable
export LD_LIBRARY_PATH=$(PWD); ./part3

part 4:
compiled source files into object files for use in library
gcc -fPIC -c driver4.c
gcc -fPIC -c memsets.c

create library from memset object file
gcc -shared -o libmemsets.so memsets.o

load library dynamically at runtime
gcc -L$(PWD) -Wall -o part4 driver4.c -lmemsets -ldl

export LD_LIBRARY_PATH to current directory and run executable
export LD_LIBRARY_PATH=$(PWD); ./part4

part 5:
put memset replacement function into injection library
gcc -shared -fPIC -o injection.so injection.c

exports LD_LIBRARY_PATH to current directory, preloads injection library, runs executable from aprt 3
export LD_LIBRARY_PATH=$(PWD);LD_PRELOAD=$(PWD)/injection.so ./part3
