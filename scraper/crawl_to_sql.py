"""crawl to sql"""
import csv

sql_header = """
use recipe_search;

INSERT INTO recipe(
    id,
    title,
    categories,
    rating,
    ratings,
    image_url,
    time,
    description,
    ingredients,
    steps,
    url)
VALUES
"""

with open(file='sample_crawl.csv', newline='', encoding='utf8') as input_file, open(file='data0.sql', mode='w', encoding='utf8') as output_file:

    output_file.write(sql_header)

    lines = input_file.readlines()
    lines = lines[1:]
    lines = map(lambda line: f'\t({line.strip()})', lines)
    lines = ',\n'.join(lines)
    output_file.write(lines)
