import requests
from bs4 import BeautifulSoup
import xml.etree.ElementTree as ET 
import time

ns = {'url': 'http://www.sitemaps.org/schemas/sitemap/0.9', 
      'image': 'http://www.google.com/schemas/sitemap-image/1.1'}

with open(file='crawl1.csv', mode='w', encoding='utf8') as file:

    file.write(','.join([
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
        ]) + '\n')

    for i in range(1, 25):
        root = ET.parse(f'sitemap-{i}.xml')

        for doc_id, url_elt in enumerate(root.findall('url:url', ns)):
            try:
                url = url_elt.find('url:loc', ns).text
            except:
                continue

            try:
                image_url = url_elt.find('image:image/image:loc', ns).text
            except:
                image_url = ''

            try:
                r = requests.get(url, timeout=33)
            except requests.exceptions.ConnectTimeout:
                continue

            soup = BeautifulSoup(r.content, 'html.parser')

            try:
                title = soup.h1.string
            except:
                continue

            try:
                breadcrumbs = soup.find(class_='breadcrumbs')
                categories = ','.join(breadcrumbs.stripped_strings)
                categories = categories.replace('Recipes,', '')
            except:
                categories = ''

            try:
                rating_div = soup.find(class_='rating')
                rating_a = rating_div.find('a', attrs={'href': '#reviews'})
                rating_str = rating_a['aria-label']
                rating = float(rating_str.split()[2])
                ratings = int(rating_str.split()[4])
            except:
                rating = 0
                ratings = 0
            
            try:
                description = soup.find(class_='recipe-description')
                description = ' '.join(description.stripped_strings)
            except:
                description = ''

            try:
                facts_label = soup.find(class_='facts__label')
                if (facts_label.string == 'Ready In:'):
                    time = facts_label.next_sibling.next_sibling.string
            except:
                time = ''

            try:
                lis = soup.find(class_='ingredient-list').findAll('li')
                ingredients = '|'.join(map(lambda x: ' '.join(x.stripped_strings), lis))
            except:
                continue
            
            try:
                lis = soup.find(class_='direction-list').findAll('li')
                steps = '|'.join(map(lambda x: x.string, lis))
            except:
                continue

            line = [
                doc_id,
                title,
                categories,
                rating,
                ratings,
                image_url,
                time,
                description,
                ingredients,
                steps,
                url
            ]

            def clean(elt):
                if isinstance(elt, str):
                    return elt.replace('"', '').replace('\n', ' ').replace('\r', ' ').strip()
                else:
                    return elt

            line = map(clean, line)

            def wrap(elt):
                if isinstance(elt, str):
                    return f'"{elt}"'
                else:
                    return str(elt)
            
            line = map(wrap, line)
            line = ','.join(line)
            line += '\n'

            file.write(line)