import pathlib
import csv
import sys
csv.field_size_limit(sys.maxsize)

recipe_index_package_dir = pathlib.Path(__file__).parent.parent
stopwords_filename = recipe_index_package_dir/"stopwords.txt"

INDEX_TXT = open(recipe_index_package_dir/"recipe_index"/"inverted_index.txt", newline='')
reader = csv.reader(INDEX_TXT, delimiter="\t")
INDEX_DICT = {}
for row in reader:
    INDEX_DICT[row[0]] = row[1:]
