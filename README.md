# Coding Challenge

Hello and welcome to this coding challenge. In this challenge, you are tasked to build a RESTful endpoint. When finished the endpoint will allow the upstream clients to search for movies given a movie genre, and return details of each found movie.

So let's get to the details, shall we?

## How it works?

We will be providing you with tasks to implement during this coding challenge (which you will find in the Github Issues section). When you are ready, you need to send us a pull request. One of our reviewers will review your pull request, give you feedback comments (sometimes you may be required to do some changes), and if all goes well merge your pull request. And then we will give you another task to implement. This will go on until we decide it is ready to move to the next step beyond the coding challenge. 

Please keep in mind:

* We will be evaluating every pull request you sent based on the rubric that we provided in the `rubric.md` file here.
* You can expect to have 2-4 tasks depending on the responses. We can also stop the coding challenge after your first pull-request.
* We will not be reviewing your story/task branches, only your pull-request.
* Please only implement what is asked in the current active task. Nothing less, nothing more. And of course pay extra attention to providing tests.
* Please DONOT publish your solutions on a public facing profile. These coding challenges are intended for internal XING interview processes.
* We will delete this repository once the final interview process is done.

## The "Restful Endpoint"

You can implement this coding challenge in the language/stack you are most proficient in. Please let us know beforehand what that is of course. 

You will receive the specs for the endpoint in the issues. The full endpoint will have a list of all matching movie details along with the actor details who are playing in that movie as well as proper error messages required. We will build up the specs gradually so be sure to be ready for some refactoring along the way.

We provided you with a `docker-compose.yml` file that has all the downstream services you would need to use to get the data you need.

* Please (if not already) install [Docker for Mac](https://docs.docker.com/docker-for-mac/install/)
* Once you clone this repo on your local machine run `docker-compose up` in your terminal window. This will download the necessary containers and bring the services up.
* When you are done, you can run `docker-compose down` to stop all the containers.

## The "Downstream" services

### Movie-Search Service
This service allows you search for movies based on genre and revenue.

`Service Address`: http://localhost:3040

`Git Repository` : https://github.com/keremk/movie-search

`Failure Percent` : This service can fail 10% of the time.

### Movie-Info Service
This service gives you detailed movie info.

`Service Address`: http://localhost:3030

`Git Repository` : https://github.com/keremk/movie-info

`Max Allowed Batch Size`: This service allows you to request a batch of maximum 5 items (through the ids query param). 

`Failure Percent` : This service can fail 20% of the time.

### Artist-Info Service
This service gives you detailed artist info.

`Service Address`: http://localhost:3050

`Git Repository` : https://github.com/keremk/artist-info

`Max Allowed Batch Size`: This service allows you to request a batch of maximum 5 items (through the ids query param). 

`Failure Percent` : This service can fail 50% of the time. Really flaky one!

## Next steps

Now if you are ready, go ahead clone this repo on your local machine. Take a look at your first task in the issues tab of this repository and get started!