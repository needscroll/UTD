# Assignment 0x1: Linking and Loading

For this assignment, you will be given a library source ([memsets.c]) that
implements different version of *memset()* functions along with driver sources
([driver0.c], [driver1.c], ...) for different parts of assignments. You need to
implement different ways to link or load library functions by carrying out
different parts of assignments. Throughout part1 ~ part5, (1) you should will
produce the executable files with the same name as build targets, which *make*
command after successfully build each target.


## Part 0 (0 point - warmup)

This part implements a single source version of memsets testcode. Run the
following command to see it running. 

```sh
$ make part0
```

Regarding Makefile syntax and its basic usages, please refer to [Makefile manual][An Introduction to Makefiles]

## Part 1: Building C source with Makefile (2pt)

For this part of assignment, you will follow a standard practice to build an
executable from multiple sources by linking object files. Please fill in
`part1:` target from Makefile to carry out the followings.

(1) Compile into objects files (driver1.o, memsets.o), and (2) link them into an
output executable (part1).

## Part 2: Creating a static library (2pt)

You can package multiple object files in to a static library, so as any program
can *copy* object files to resolve dependencies and build an output binary.
You will fill in `part2` target of Makefile.

(1) Compile into objects files, (2) package `memsets.o` into an archive (`libmemsets.a`), and
(3) statically link `libmemsets.a` and `driver1.o` to produce an output executable (`part2`)(4) Run
the produced executable.

## Part 3: Creating a dynamic library (3pt)

Now, it is time to build a shared library (`libmemsets.so`). Please update
Makefile (target `part3`) to carry out the following steps.

(1) Compile into objects files, (2) package `memsets.o` into an shared library (say `libmemsets.so`),
(3) compile driver1.c into an output executable (part3), and (4) dynamically link shared library to run the program.

## Part 4: Dynamically load a library (3pt)

For this part of assignment, you will modify driver4.c to use functions from
[dlfnc.h] to dynamically load necessary functions from shared library
(`libmemsets.so`) from part3 at runtime. Fix driver4.c to replace
__replace_me__ to appropriate variables and update Makefile accordingly. 

## Part 5: Library interposing with LD_PRELOAD (bonus 5pt)
 
This assignment is again based on part3. While you run the output executable
from part3, you will intercept library loading (LD_PRELOAD) to replace *memset()*
from libc library to something else (e.g., `memset1`).

## Files to summit

* Updated sources + Makefile.
* [solution.md](solution.md) A briefly write-up to explain your solutions. 

Zip above files, and sign with your public key and upload it to [eLearning Assignments].

<img src="https://codimd.syssec.org/uploads/upload_a8cf3019295605bf89469fad20b4caa6.png"  width="600">


----
[driver0.c]:driver0.c
[driver1.c]:driver1.c
[driver4.c]:driver4.c
[memsets.c]:memsets.c
[memsets.h]:memsets.h
[Makefiles]:Makefiles

[An Introduction to Makefiles]:https://www.gnu.org/software/make/manual/html_node/Introduction.html
[dlfcn.h]:https://linux.die.net/man/3/dlopen
[eLearning Assignments]:https://elearning.utdallas.edu/webapps/blackboard/content/listContentEditable.jsp?content_id=_3262367_1&course_id=_180521_1&mode=reset