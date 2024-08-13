#!/usr/bin/env python3
"""Map 3"""

import sys
import math

fN = open("total_document_count.txt")
N = int(next(fN))
fN.close()

for line in sys.stdin:
    word, docid, tf, nk = line.split()
    idf = math.log10(float(N)/float(nk))
    print(f"{docid}\t{word} {idf} {tf}")
