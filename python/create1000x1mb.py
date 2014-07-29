import os
import sys

def create1mbFile(filename):
    fp = open(filename, "wb")
    fp.write(bytes('0' * 1024 * 1024 ,"ASCII"))
    fp.close()

base = "."

if(len(sys.argv) > 1):
    base = sys.argv[1];


for level1 in range (0, 10):
    for level2 in range (0, 10):
        dirpath = os.path.join(base, "a" + str(level1), "b" + str(level2))
        os.makedirs(dirpath)
        for filename in range (0, 10):
            create1mbFile(os.path.join(dirpath, "file" + str(filename)))


