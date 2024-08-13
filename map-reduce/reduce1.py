#!/usr/bin/env python3
"""Reduce 1"""

import sys

current_key = None
counts = {}
for line in sys.stdin:
    word, docid = line.strip().split()
    key = f"{word}\t{docid}"
    if key not in counts:
        counts[key] = 1
    else:
        counts[key] += 1
    if current_key == None or counts[key] == 1:
        current_key = key

for key, tf in counts.items():
    # word docid count
    word, docid = key.split()
    print(f'{word}\t{docid}\t{tf}')
