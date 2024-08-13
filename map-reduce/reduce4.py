#!/usr/bin/env python3
"""Reduce 4"""

import sys

CURRENT_KEY = None
terms = {}
for line in sys.stdin:
    word, idf, docid, tf, norm = line.split()
    key = word
    obj = {"idf": idf, "docid": docid, "tf": tf, "norm": norm}
    if key not in terms:
        terms[key] = [obj]
    else:
        terms[key].append(obj)
    if CURRENT_KEY is None or len(terms[key]) == 1:
        CURRENT_KEY = key

for term, values in terms.items():
    sout = f"{term}"
    for obj in values:
        for value in obj.values():
            sout += f"\t{value}"
    print(sout)
