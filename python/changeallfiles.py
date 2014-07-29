import os
import sys

def append1kbtoFile(filename):
    fp = open(filename, "ab")
    fp.write(bytes('a' * 1024 ,"ASCII"))
    fp.close()

base = "."

if(len(sys.argv) > 1):
    base = sys.argv[1];


level1 = base;
for level2 in range (0, 10):
    dirpath = os.path.join(level1, "b" + str(level2))
    for filename in range (0, 10):
        append1kbtoFile(os.path.join(dirpath, "file" + str(filename)))


