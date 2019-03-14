## Build the Docker Image

* Download the source code from Github
`git clone git@github.com:username/reponame.git`
* Change into code directory
`cd reponame`
* Checkout branch (optional)
  ***master*** for OpenAPI 3.0.1
  `git checkout master`
* Compile the war (Web Application Archive) file with maven
  `mvn clean package`
* Build the docker image (replace dockerhubuser with your docker hub account user)
  `docker build -t dockerhubuser/reponame:tag .`
* Check images
  `docker images`

## Run the Docker Container

* Run the image as a local container 
`docker run -p 8080:8080 --link mongodb:mongodb user/reponame:tag`
* If you run the container locally don't forget to start also a mongodb container as a data base with:
`docker pull mongo; docker run --name mongodb -d mongo`
* Load the Swagger-UI representation in a web-browser
e.g.: `firefox http://0.0.0.0:8080`

## Run the containers with Docker Compose

* Check the docekr-compose.yml file. Verify that the image under the jguweka service is in your local file. If not, change the image for your weka REST image that you build in the first part of this README
* Run the command :
`docker-compose up`
* To take down the services, press Ctrl + C or run the command: 
`docker-compose down`

## Push docker image to dockerhub

*Tag you image correctly, run this command if necessary
`docker tag local-image:tagname reponame:tagname`
*Login you docker account if not already done.
`docker login`
*Push your image to your account
`docker push reponame:tagname`

