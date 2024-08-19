"""crawl to sql"""
import csv

sql_header = "use recipe_search;\n"

with open(file='crawl.csv', newline='', encoding='utf8') as input_file, open(file='data.sql', mode='w', encoding='utf8') as output_file:

    output_file.write(sql_header)

    lines = input_file.readlines()
    # lines = lines[1:]
    insert_statement = "INSERT INTO recipe( id, title, categories, rating, ratings, image_url, time, description, ingredients, steps, url) VALUES"
    lines = map(lambda line: f'{insert_statement} ({line.strip().replace("\\", "")});', lines)
    lines = '\n'.join(lines)
    output_file.write(lines)
