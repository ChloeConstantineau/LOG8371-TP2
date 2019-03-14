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

* If you run the container locally don't forget to start also a mongodb container as a data base with:
`docker pull mongo; docker run --name mongodb -d mongo`
* Run the image as a local container 
`docker run -p 8080:8080 --link mongodb:mongodb user/reponame:tag`
* Load the Swagger-UI representation in a web-browser
e.g.: `firefox http://0.0.0.0:8080`

## Run a docker swarm

* First create your swarm
`docker swarn init`
* Deploy you swarm with the configurations in your docekr-compose.yaml
`docker stack deploy -c docker-compose.yml tp2`
* Attest to your many services/containers/stack by running with these commands :
`docker service ls`
`docker container ls -q`
`docker stack ps`
* If you want to change your configuration for example the number of replicas, simply modify your docker-compose.yaml, save the file and run the following command :
`docker stack deploy -c docker-compose.yml tp2`

* To take down your stack:
`docker stack rm getstartedlab`
* To remove your swarm :
`docker swarm leave --force`


## Push docker image to your repository on dockerhub

*Tag you image correctly, run this command if necessary
`docker tag local-image:tagname reponame:tagname`
*Login you docker account if not already done.
`docker login`
*Push your image to your account
`docker push reponame:tagname`

