#!/usr/bin/env python3
"""Reduce 2"""

import sys

current_key = None
nk = {}
lines = []
for line in sys.stdin:
    lines.append(line)
    word, docid, tf = line.split()
    key = word
    if key not in nk:
        nk[key] = 1
    else:
        nk[key] += 1
    if current_key == None or nk[key] == 1:
        current_key = key
for line in lines:
    word, docid, tf = line.split()
    print(f"{word}\t{docid}\t{tf}\t{nk[word]}")
