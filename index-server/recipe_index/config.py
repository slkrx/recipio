import pathlib
import csv
import sys
import math
csv.field_size_limit(sys.maxsize)

recipe_index_package_dir = pathlib.Path(__file__).parent.parent
stopwords_filename = recipe_index_package_dir/"stopwords.txt"

INDEX_TXT = open(recipe_index_package_dir/"recipe_index"/"inverted_index.txt", newline='')
reader = csv.reader(INDEX_TXT, delimiter="\t")
INDEX_DICT = {}
for row in reader:
    value = []
    values = row[1:]
    for i in range(0, len(values), 4):
        idf, docid, doctf, norm = values[i:i+4]
        if i == 0:
            value.append(idf)
        value.append(docid)
        value.append(float(doctf)*float(idf)/math.sqrt(float(norm)))
    INDEX_DICT[row[0]] = value
