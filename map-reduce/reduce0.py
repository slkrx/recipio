#!/usr/bin/env python3
"""Reduce 0"""

import sys
import re
import csv
csv.field_size_limit(sys.maxsize)

# Doc count
COUNT = 0
for line in sys.stdin:
    COUNT += 1
fout = open("total_document_count.txt", "w")
fout.write(f"{COUNT}")
fout.close()

# Input file processing
stopwords = []
with open("stopwords.txt") as fstopwords:
    for word in fstopwords:
        stopwords.append(word.strip())

with open("input/input.csv", newline="") as csvfile:
    reader = csv.DictReader(csvfile, fieldnames=["doc_id", "doc_body"])

    for row in reader:
        for word in row["doc_body"].split():
            word = re.sub(r'[^a-zA-Z]+', '', word).lower()
            if word and word not in stopwords:
                print(f'{word}\t{row["doc_id"]}')
