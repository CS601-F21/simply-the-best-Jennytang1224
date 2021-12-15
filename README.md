![Alt text](https://i.ibb.co/K63f3mG/Screen-Shot-2021-12-14-at-10-26-13-PM.png?raw=true "Title")

# Simply The Best Project:

## Idea: 
This project is based on a real life problem to solve: we all know yelp restaurant rating is an overall rating, it doesn't tell the rating on a specific type of food. So for people who have high standard or really into certain dish ( let’s say margarita pizza, Turkish coffee, gluten-free pancakes ), we have no idea if these food are good in a restaurant.  my solution is why not we build a program to find the places with best of that type food in town. .

## Implementation Details: 
### Acquire data: 

Data downloaded from Kaggle

### Process Data:
Use pythons Jupiter notebook to process and analyze data. Due to the large volume of the data, I did segmentation on data and only focus on:
- Among all 160,585 businesses, there're 29,469 businesses about food. I only focus on Food related business by filtering the business contains "food" in their category.
- food in Portland - because Portland has the most food related businesses (3935 businesses) among all other 395 cities 
- Only look at the reviews since 2018 (most recent 3 years) because they are more reliable comparing to the reviews from 10 years ago
- After date preprocessing, I'm keeping 112,381 most recent 3 years reviews, related to 3935 food related businesses in Portland.

### Search in the reviews:

Main approach: 

If review is 5 stars + mentioned keyword “croissant” (We can't naively assume “croissant” mentioned here is the reason it got 5 stars, because reviewers could also say: “everything is great, but I haven’t tried their croissant…”)
So I used Sentiment analysis to tell if a review is 5 stars and see if the sentence includes the food you are searching for is classified as positive, negative or neutral.  
            
Simple example:

            “Wow! Their croissant is amazing” -> positive;
            “Don’t try their almond croissant!” -> negative

A little more complex example:

            “Overall is a 10 out 10! their croissant can be a little crunchier though” -> positive review but negative on croissant ;
            “their food is not impressive, the only thing I came for is their delicious soup” -> negative review but positive on soup;



Details:
- store reviews in review object with id and use reviewIds to get reviews, keep only 5 stars reviews

- select sentence only contains the term in the review, (filter out sentence more than 15 words long) 

- use sentiment analysis to count the reviews rated as "positive" or "very positive"

- create the map for business with positive reviews for this term and sort them by number of positive reviews

- return top 3 businesses with the most positive reviews toward the term


Challenges:

I'm using StanfordCoreNLP tool for sentiment analysis, it took a while to run since its checking word by word see if they are positive or negative and generate the average score of the sentence to decide if its a positive sentence or negative. Therefore, I removed the sentence contains the term that is longer than 15 words for the performance purpose. 



## UI
- User input: 
	
        “croissant” 

- Output: The info of top 3 places with best croissant, sorted by frequency of people positively mentioned it
        
        #1: “Le Croissant Café,  1151 Broadway Ave, Burlingame, CA 94010,  72 positive comments on “croissant”
        #2: “Farm: Table,  333 Geary Blvd, San Francisco, CA 94108,  57 positive comments on “croissant”
        #3: ”Acme Bread Company,  1 Ferry Bldg, San Francisco, 94111,  40 positive comments on “croissant”


## run the program:
It's running on localhost port 8081, so use the link: http://localhost:8081/simplythebest after starting the server

## Future work:
- Scrape the yelp website to get San Francisco reviews and build an app focus on San Fransisco
- Try Tensor Flow to improve the accuracy on sentiment analysis
- In near term I'm using a simple website, in the future I would like to make a mobile app based on this program, and user input the name of the food and output the restaurant information. 