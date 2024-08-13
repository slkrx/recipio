"""all recipes scraper"""
import xml.etree.ElementTree as ET
import time
import requests
from bs4 import BeautifulSoup

root = ET.parse('sitemap_1.xml')
with open(file='crawl0.csv', mode='a', encoding='utf8') as file:
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

    for doc_id, loc in enumerate(root.findall(
        '{http://www.sitemaps.org/schemas/sitemap/0.9}url/{http://www.sitemaps.org/schemas/sitemap/0.9}loc')):

        url = loc.text
        try:
            r = requests.get(url, timeout=33)
        except requests.exceptions.ConnectTimeout:
            continue

        soup = BeautifulSoup(r.content, 'html.parser')

        if soup.h1 is not None:
            title = soup.h1.string.replace('"', '')
        else:
            continue

        recipe_details = soup.findAll(class_='mm-recipes-details__label')
        if recipe_details is not None:
            total_time_list = list(filter(lambda x: x.string == 'Total Time:', recipe_details))
            if len(total_time_list) > 0:
                time = total_time_list[0].next_sibling.next_sibling.string
            else:
                time = ''
        else:
            time = ''


        if soup.find(class_='primary-image__image') is not None:
            if soup.find(class_='primary-image__image').has_attr('src'):
                image_url = soup.find(class_='primary-image__image')['src']
            elif soup.find(class_='primary-image__image').has_attr('data-src'): 
                image_url = soup.find(class_='primary-image__image')['data-src']
            else:
                image_url = ''
        elif soup.img and soup.img.has_attr('src'):
            image_url = soup.img['src']
        elif soup.img and soup.img.has_attr('data-src'):
            image_url = soup.img['data-src']
        else:
            image_url = ''

        if soup.find(class_='mm-recipes-review-bar__rating-count') is not None:
            ratings = int(soup.find(class_='mm-recipes-review-bar__rating-count').string.strip('(').strip(')').replace(',', ''))
        else:
            ratings = 0

        if soup.find(class_='mm-recipes-review-bar__rating') is not None:
            rating = float(soup.find(class_='mm-recipes-review-bar__rating').string)
        else:
            rating = 0.0

        if soup.find(class_='breadcrumbs') is not None:
            categories = ','.join(soup.find(class_='breadcrumbs').stripped_strings).replace('Recipes,', '')
        else:
            categories = ''

        if soup.find(class_='article-subheading') is not None:
            subtitle = soup.find(class_='article-subheading').string or ''
        else:
            subtitle = ''

        if soup.find(class_='mm-recipes-intro') is not None:
            intro = ' '.join(soup.find(class_='mm-recipes-intro').stripped_strings) or ''
        else:
            intro = ''

        if soup.find(class_='mm-recipes-structured-ingredients') is not None:
            ingredients = '|'.join(
                map(lambda x: ' '.join(x.stripped_strings), 
                    soup.find(class_='mm-recipes-structured-ingredients')
                    .findAll('li')))
        else:
            continue

        if soup.find(class_='mm-recipes-steps') is not None:
            steps = '|'.join(soup.find(class_='mm-recipes-steps').stripped_strings).replace('Directions|', '') or ''
        else:
            continue

        line = [
            doc_id,
            title,
            categories,
            rating,
            ratings,
            image_url,
            time,
            subtitle + ' ' + intro,
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