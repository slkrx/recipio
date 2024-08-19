"""crawl combiner"""
with(open(file='crawl0.csv', encoding='utf8') as input_file1,
    open(file='crawl1.csv', encoding='utf8') as input_file2,
    open(file='crawl.csv', mode='w', encoding='utf8') as output_file):
    i = 1
    for row in input_file1:
        output_file.write(f'{i},{','.join(row.split(',')[1:])}')
        i += 1
    for row in input_file2:
        output_file.write(f'{i},{','.join(row.split(',')[1:])}')
        i += 1
