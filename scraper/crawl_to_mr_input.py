"""crawl to mr input"""
import csv

with open(file='sample_crawl.csv', newline='', encoding='utf8') as input_file, open(file='input.csv', mode='a', encoding='utf8') as output_file:
    reader = csv.DictReader(input_file, fieldnames=[
        'id',
        'title',
        'categories',
        'rating',
        'ratings',
        'image url',
        'time',
        'description',
        'ingredients',
        'steps',
        'url'
    ])
    for i, row in enumerate(reader):
        if i == 0:
            continue
        doc_body = row['title'] + ' ' + \
            ' '.join(row['categories'].split(',')) + ' ' + \
            row['description'] + ' ' + \
            ' '.join(row['ingredients'].split('|')) + ' ' + \
            ' '.join(row['steps'].split('|'))
        output_file.write(f'{row['id']},"{doc_body}"\n')
