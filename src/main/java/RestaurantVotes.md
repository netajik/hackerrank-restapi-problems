Rest API: Total Votes
Problem
A restaurant rating application collects ratings or votes from its users and stores them in a database. They want to allow users to retrieve the total vote count for restaurants in a city. Implement a function, getVoteCount. Given a city name and the estimated cost for the outlet, make a GET request to the API at https://jsonmock.hackerrank.com/api/food_outlets?city=<cityName>&estimated_cost=<estimatedCost> wheren and are the parameters passed to the function.

The response is a JSON object with the following 5 fields:

* page: The current page of the results. (Number)
* per_page: The maximum number of results returned per page. (Number)
* total: The total number of results. (Number)
* total_pages: The total number of pages with results. (Number)
* data Either an empty array or an array with a single object that contains the food outlets’ records.

In data, each food outlet has the following schema:

* id: The outlet ID. (Number)
* name: The name of the outlet. (String)
* city: The city in which the outlet is located. (String)
* estimated_cost: The estimated cost of the food in the particular outlet. (Number)
* user_rating: An object containing the user ratings for the outlet. The object has the followgiting schema:
* average_rating: The average user rating for the outlet. (Number)
* votes: The number of people who voted the outlet. (Number)

If there are no matching records returned, the data array will be empty. In that cases, the getVoteCount function should return -1.

An example of a food outlet record is as follows:

````json
{
"city": "Seattle",
"name": "Cafe Juanita",
"estimated_cost": 160,
"user_rating": {
"average_rating": 4.9,
"votes": 16203
},
"id": 41
}
````
Use the votes property of rach outlet to calculate the total vote count of all the matching outlets.

###Function Description

Complete the getVoteCount function in the editor below.

getVoteCount has the following parameters:
* cityName: The city to query. (String)
* estimatedCost: The cost to query. (Integer)

Returns
* Integer: the sum of votes for matching outlets or -1.

Constraints
*No query will return more than 10 records.

Sample Cases

*Sample Case 0

Input: "Seattle", 110
Output: 2116
Explanation: First a call is made to API https://jsonmock.hackerrank.com/api/food_outlets?city=Seattle&estimated_cost=110 tp fetch the only matching outlet. The sum of votes is calculated and returned.

*Sample Case 1

Input: "Delaware", 150
Output: -1
Explanation: An API call is made to https://jsonmock.hackerrank.com/api/food_outlets?city=Delaware&estimated_cost=150 tp fetch the only matching outlet. The sum of votes is calculated and returned.

Testing
Below are the sample test cases and their expected results:

Sample Case #	cityName	estimatedCost	Result
        0	    “Seattle”	110	            2116
        1	    “Delaware”	150	            -1
        2*	    “Miami”	    120	            3682
        3	    “Miami”	    110	            1770
        4*	    “Omaha”	    60	            5078
        5*	    “Dallas”	70	            1592
        6*	    “Dallas”	100	            0
        7*	    “Denver”	200	            0
        8*	    “Houston”	350	            732

Reference:
* https://giltroymeren.github.io/hackerrank-2021-front-end-exam-part-2/