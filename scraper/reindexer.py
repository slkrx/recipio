"""reindexer"""

with open(file='crawl0.csv', newline='', encoding='utf8') as input_file, open(file='crawl00.csv', mode='w', encoding='utf8') as output_file:
    for i, row in enumerate(input_file):
        if i == 0:
            continue
        output_file.write(f'{i},{','.join(row.split(',')[1:])}')
