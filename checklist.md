# Capstone Checklist

## User Stories
>1. As a casual user, I want to search for recipes and be able to compare them to decide which one I want to cook.
>2. As an authenticated user, I want to be able to save recipes that I liked so I can come back to them later.
>3. As an authenticated user, I want to be recommended recipes that I may be interested in cooking that I may otherwise not have searched for.
>4. As an authenticated user, I want to be able to create my own recipes to be able to share them.
>5. As an authenticated user with an organization, I want to be able to create an organization with other users as members.
>6. As an authenticated user with an organization, I want to share my recipes and other recipes with my organization.

## User Story Technical Breakdown
>### User Story \#1
>#### Search Engine
>- Data
>    - Recipes will be scraped from the web
>    - Need to consider what data about the recipes to save and what to discard
>        - For instance, while a long introduction is what I'm trying to avoid in the UI, a short description could provide valuable information to the search engine
>    - Decide which websites to scrape data from
>    - Create a web scraper in Python
>    - Saved in a csv file with complete and formatted data
>- Inverted Index
>    - This will be a txt file that maps a term to it's document (in this case my documents are recipes) id and the term-frequency, inverse-document-frequency (tfidf) score of the term in relation to the document.
>        - this implies I will be assigning ids to recipes before inserting them in the database, so I will have to be careful with this because it will not be automatically managed by the DBMS
>    - Input: a csv file of the recipes reduced to the recipe id, and all text data associated with the recipe with nothing but alphabetic characters
>    - Output: the inverted index
>    - Methodology: implementing Map Reduce with Hadoop
>        - This data will be huge but we can take advantage of the fact that much of the work does not need to know about each other and can be conducted in parallel so the time it takes to build the inverted index will not be overwhelming
>        - Values that need to be extracted to build [tfidf](https://en.wikipedia.org/wiki/Tf%E2%80%93idf) scores:
>            - \# of recipes
>            - \# of times each term appears in each document
>            - \# of documents containing each term
>- Inverted Index Server
>    - Once this is created I want to create a server that will load the data in the inverted index into an in-memory hash-map so that I can access each term's tfidf score for each document it appears in in constant time
>        - The idea being that this will be a very large text file and the program itself will use a lot of CPU memory so potentially I could deploy this program separately to it's own server and use it as API
>        - This API will receive a search query and a page \#
>        - It will return an list of document ids sorted by relevancy based on the [cosine similarity](https://en.wikipedia.org/wiki/Vector_space_model) of the query vector to each document's term vector.
>            - e.g. if the query is "chicken tikka masala" the query vector is [tfidf("chicken"), tfidf("tikka"), tfidf("masala")] with regard to the query and the document vectors are the same but with regard to that document and while they technically are of length equivalent to the number of terms in the vocabulary these values don't matter because when we take the dot product of the two they will become zero.
>        - This list could potentially be quite large so the page \# will allow us to return a limited subset of results and paginate in our frontend
>    - This will be a python Flask server because it will allow me to make a fairly light-weight server as it wont be doing much whereas Springboot has a lot of overhead.
>    - Stretch goal: weight the tfidf scores with a normalized score based on a 1-5 review rating and number of reviews
>- Document Database
>    - This is where I'll implement a Springboot application
>    - While the inverted index does contain all text data associated with a given recipe/document, it is completely unformatted and we don't have a way to reformat this text.
>        - Therefore, I will store the fully-formatted text data along with any extra data I wish to include about recipes in a database.
>    - The idea here is that we take a search query of terms, transform that into a set of recipe ids in constant time by making a request to the index server, and then use those ids to retrieve the recipes in constant time from the database.
>        - This way, we are never traversing the data in it's entirety.
>    - Will need to convert the full data csv into a sql file to insert data into the database
>- Document Database Server
>    - This is the API the frontend will interface with
>    - I want to implement server-side dynamic pages here because a user should enter an entire query and then press enter to retrieve the results.
>        - It makes more sense to me to model this as a server-side rendered page because there isn't really anything we can do with each keystroke the user enters and not much we can do while we wait for results. To wait for a JSON object of recipes and then render them dynamically client-side doesn't make much sense either, that object would be just about as large as the HTML file we need to serve.
>        - I plan to use [thymeleaf](https://www.thymeleaf.org/) for this.
>        - This will also allow me to develop search results independent of the frontend
>- Frontend Application
>    - With regards to this task this will really just be a placeholder for future work and perhaps enclosing the server-side rendered pages within a container so that it can dynamically display a loading spiral gif while the search engine retrieves the results.
>### User Story \#2
>#### User Database
>- In order to accomplish this I first need to add users to the database
>    - I'll do this by implement Spring Security like we did in class
>- This is where it makes the most sense to do client-side dynamic pages, so the frontend will be a react app that makes API requests to the Springboot server which will manage the recipe database and user database tables.
>- There will have to be a many-to-many relationship between users and recipes.
>### User Story \#3
>#### Recommender Algorithm
>- I am going with the [information filtering](https://en.wikipedia.org/wiki/Information_filtering_system) approach rather than a collaborative filtering approach because I don't have the users needed to implement that.
>- I will need to require the user to add some preferences to their profile in order to recommend new recipes.
>- I need to do some research on how this will work but the basic idea for now is that the user's preferences will be a term vector that I will use to compute the cosine similarity with recipe vectors to find which recipes most closely resemble the user's preferences
>    - This would be a good situation to have the stretch goal of weighting by reviews implemented as it could make this feature much more useful to the user.
>### User Story \#4
>#### Recipe CRUD
>- This will be the basic CRUD functionality for recipes the user creates.
>- I'm not including them in search results because it doesn't make sense to rebuild the index every time a user creates a new recipe. The whole point of the index is to front-load a huge amount of computation and reference it later.
>### User Story \#5, \#6
>#### Organizations
>- This will add organizations to the database. Users can create organizations with a few details about them but most importantly can add other app users to their organization to access a shared list of recipes. This is what makes the recipe CRUD functionality the most useful, but they can also add other recipes they found through search to the list of recipes.

## Tasks
>- [x] write web scraper - 1hr
>- [x] write map and reduce scripts for hadoop - 1hr
>- [ ] write inverted index server with sample index - 1hr
>- [x] create a sample inverted index and test server - 1hr
>- [ ] create recipe database ddl and insert sample data - 1hr
>- [x] write csb -> sql converter - 1hr
>- [ ] create recipe database server and add required dependencies - 1hr
>- [ ] add an API route for search database by query, this makes a request to the index API - 1hr
>- [ ] repository layer find by id, used by search route to retrieve recipes by ids returned from index API - 1hr
>- [ ] test repository and domain layers for search - 1hr
>- [ ] add server side dynamic pages for searching - 4hrs
>- [ ] add users to the database - 1hr
>- [ ] implement spring security as we did in class - 2hrs
>- [ ] test users repository layer - 2hrs
>- [ ] create a React app for the frontend that contains the search pages - 1hr
>- [ ] add functionality to React app to allow users to create accounts and sign in - 4hrs
>- [ ] add many to many relationship between users and recipes in the database - 1hr
>- [ ] test user-recipe repository layer - 1hr
>- [ ] create sidebar and saved recipes in react app - 1hr
>- [ ] create API functionality for users to create a user-recipe relationship in database - 2hrs
>- [ ] test user-recipe domain layer - 1hr
>- [ ] create functionality for users to save recipes in frontend - 1hr
>- [ ] create recipe component to display in saved recipes component - 1hr
>- [ ] add a discover tab to sidebar - 1hr
>- [ ] research recommender algorithms - 4hrs
>- [ ] implement recommender algorithm in the backend - 1hr
>- [ ] test recommender in domain layer - 1hr
>- [ ] implement api fetches to discover route in backend in frontend - 1hr
>- [ ] implement recipes being populated in discover tab in frontend - 1hr
>- [ ] create recipe repository - 1hr
>- [ ] create recipe domain - 1hr
>- [ ] create recipe repository and domain testing - 1hr
>- [ ] create recipe controller - 1hr
>- [ ] create recipe frontend - 1hr
>- [ ] view recipes repository and domain - 1hr
>- [ ] view recipes by user id
>- [ ] view recipes controller and frontend - 2hr
>- [ ] test view recipes domain and repository - 1hr
>- [ ] update recipe repository - 1hr
>- [ ] update recipe domain - 1hr
>- [ ] test update recipe repository and domain layer - 1hr
>- [ ] update recipe controller - 1hr
>- [ ] update recipe frontend - 1hr
>- [ ] delete recipe repository - 1hr
>- [ ] delete recipe domain - 1hr
>- [ ] test delete recipe repository and domain layer - 1hr
>- [ ] delete recipe controller - 1hr
>- [ ] delete recipe frontend - 1hr
>- [ ] create organization repository - 1hr
>- [ ] create organization domain - 1hr
>- [ ] create organization repository and domain testing - 2hr
>- [ ] create organization controller - 1hr
>- [ ] create organization frontend - 1hr
>- [ ] add users to organization repository - 1hr
>- [ ] add users to organization domain - 1hr
>- [ ] add users to organization repository and domain testing - 2hr
>- [ ] add users to organization controller - 1hr
>- [ ] add users to organization frontend - 1hr