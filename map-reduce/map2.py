#!/usr/bin/env python3
"""Map 2"""

import sys

for line in sys.stdin:
    word, docid, tf = line.split()
    print(f"{word}\t{docid} {tf}")
