#!/usr/bin/env python3
"""Map 1"""

import sys

for line in sys.stdin:
    word, doc_id = line.split()
    print(f'{word}\t{doc_id}')
