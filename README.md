# Project

## Idea: 
This project is based on a real life problem to solve: we all know yelp resutant rating is an overall rating, it doesnt tell the rating on a specific type of food. So for people who have high standard or really into certain dish ( let’s say margarita pizza, Turkish coffee, gf pancakes ), we have no idea if these food are good in a resurant.  my solution is why not we build a program to find the places with best of that type food in town. .

## Implementation Details: 
### To aquire data: 
I have planned 3 ways to aquire data:
- Dynamic data: 
    Write scraping script:
    use online tools like octoparse (click and drag) (-> these two ways will give us more updated data )
    download data from online recource (Kaggle):  business info, reviews… offline data 

- static data: 
    I’m planning to download the data to build my program and if i have more time i will write a scrapper to get more updated data)
    to process the reviews. 

### To search in reviews:
- To search in review data and looking for the keyword user input, keep track of a counter to count frequency people liked it. 

- Main approach: If review is 5 stars + mentioned keyword “croissant” (naively assume “croissant” mentioned here is the reason it got 5 stars, but they could also say: “everything is great, but I haven’t tried their croissant…”)

- Additional approach: Use Sentiment analysis to tell if a review includes the food you are searching for is classified as positive, negative or neutral.  
	Example: “Wow! Their croissant is amazing” -> positive;
		    “Don’t try their almond croissant!” -> negative


## UI
- User input: 
	
        “croissant” 
- Output: The info of top 3 places with best croissant, sorted by frequency of people positively mentioned it
        
        #1: “Le Croissant Café,  1151 Broadway Ave, Burlingame, CA 94010,  72 positive comments on “croissant”
        #2: “Farm: Table,  333 Geary Blvd, San Francisco, CA 94108,  57 positive comments on “croissant”
        #3: ”Acme Bread Company,  1 Ferry Bldg, San Francisco, 94111,  40 positive comments on “croissant”




## Future work:
- in near term I will use a command line 
- in the future I would like to make a mobile app based on this program, and user input the name of the dish and output the resturant information. 