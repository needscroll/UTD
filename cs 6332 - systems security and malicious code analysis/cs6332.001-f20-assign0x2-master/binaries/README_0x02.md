Assignment Instruction
---------------------

For this part, you will craft an input to overwrite the stack with shellcode
and transfer control the beginning of shellcode as main() function returns.
You can google for execve() shellcode x86 that would eventually run /bin/sh
command for you. ASLR has been disabled for your ease, address space layout,
and addresses of functions and variables won't change across different
executions.

From this part, you will save your payload as *a file* and provide it as an
arguement to the vulnerable program (part0x02).

    echo -ne "payload\xef\xbe\xad\xde" > /tmp/myinput
    ./part0x02 /tmp/myinput
    CS6332 Crackme Level 0x00
    Invalid Password!

NOTE: The submission server (and account for each part) is shared by the entire
class, please try to use a unique filename for your input to avoid potential
conflict.

Upon a successful exploitation, you will see the shell prompt as below.

    part0x02@cs6332-lab0:~$ ./part0x02 /tmp/input2
    CS6332 Crackme Level 0x00
    Invalid Password!
    $ id
    uid=1010(assign0x2-p2) gid=1014(assign0x2-p2) groups=1014(assign0x2-p2),1015(assign0x2-p2-pwn)
    $ ./solve
    your netid is xxxx, turn in the following hash value.
    326130ad74d411d4e92807fad05762ed


PRO TIP: Even with ASLR, stack location may vary slightly due to environment
variables. You may consider padding your payload with sled (NOP instruction) to
make your exploit robust.
 
PRO TIP: if you want to make your environment as similar as possible, prepend
`env -i` before your program command, i.e. `env -i ./part0x02`. 

Submission
----------

On successful exploitation, the program will run the `solve` program, which
will ask you to provide your NetID and return your hash value. For this part of
the assignment, please submit the followings

  1. Your *input* to exploit the buffer overflow vulnerability and deliver shellcode payload.
  2. Hash value generated by *solve* as a return for your NetID.