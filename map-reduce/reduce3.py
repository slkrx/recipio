#!/usr/bin/env python3
"""Reduce 3"""

import sys
import math

current_key = None
tfs2 = {}
lines = []
for line in sys.stdin:
    lines.append(line)
    docid, word, idf, tf = line.split()
    key = docid
    value = math.pow(float(tf), 2) * math.pow(float(idf), 2)
    if key not in tfs2:
        tfs2[key] = value
    else:
        tfs2[key] += value
    if current_key == None or tfs2[key] == value:
        current_key = key

for line in lines:
    docid, word, idf, tf = line.split()
    norm = tfs2[docid]
    print(f"{word}\t{idf}\t{docid}\t{tf}\t{norm}")
