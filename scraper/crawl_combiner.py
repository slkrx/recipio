"""crawl combiner"""
with open(file='crawl1.csv', mode='w', encoding='utf8') as output_file:
    j = 50205
    for i in range(4,16):
        with open(file=f'crawl{i}.csv', newline='', encoding='utf8') as input_file:
            for i, row in enumerate(input_file):
                if i == 0:
                    continue
                output_file.write(f'{j},{','.join(row.split(',')[1:])}')
                j += 1
