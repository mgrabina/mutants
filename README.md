# X-Men Mutants Detector
*This document includes also design specifications that may help the reader to understand how the system was developed and thinked, for build instructions please scroll down.*

## Design
It's a multi-module project, which includes model, interfaces, persistence, service and webapp modules.
Made with Java (Maven, Spring, JUnit, Jersey, ...), designed to scale (in runtime and in functionality), 
and focused in reducing time complexity in all layers.

## API
It is recommended to use:

*AWS Elastic Beanstalk is an easy-to-use service for deploying and scaling web applications and services developed with Java (among others). For more information regarding how autoscale works in Elastic Beanstalk please visit: https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/using-features.managing.as.html (basically auto manages EC2 instances on demand based on triggers such as requests quantity that can be configured at Beanstalk's environments)*

The API was stress tested with two tools: 

- https://artillery.io/
- https://github.com/paoliniluis/node-load-tester-graph

And monitored via the AWS Elastic Beanstalk's monitoring tool, indicating expected behaviour for more than 1000 requests per second.

### Requests
#### Detection Test
    $   POST /mutants
        Content-Type: application/json
        {
            “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
        }
 
 Returns
 
Reponse Code | Description
--- | ---
200 OK | Is Mutant
403 Forbidden | Is Human
400 Bad Request | The input was invalid  

#### Retrieve Statistics
    $   GET /stats
        Accept: application/json
 
 Returns
    
    $ {  
          “count_mutant_dna”:40, 
          “count_human_dna”:100: 
          “ratio”:0.4
      }
  

## Build
The following instructions will set the development environment in your local machine, as well as let you run locally an instance of the system.

### Prerequisites

1. Clone the repository or download source code:

	```
	$ git clone https://github.com/mgrabina/mutants.git
	```
	

2. Install Maven
	#### Mac OS X
	```
	$ brew install maven
	```

	#### Ubuntu
	```
	$ sudo apt-get install maven
	```

	#### Other OSes
	Check https://maven.apache.org/install.html.

3. **(Optional)** Install PostgreSQL, if you want to use a local database.

	#### Mac OS X
	```
	$ brew install postgresql
	```

	#### Ubuntu
	```
	$ sudo apt-get install postgresql
	```
 
### Build

1. Install artifacts:

	```
	$ cd <project-root>
	$ mvn clean install
	```

	Doing this will let you access all modules defined in the project scope.

2. **(Optional)** Configure custom database.

    In *<project-root>/mutants-webapp/src/main/java/meli/webapp/config/WebConfig.java*, changing the parameters of the **dataSource()**. 

3. Build project itself:

	```
	$ mvn clean package
	```

	This will create a *.war* (Web ARchive) file inside <project-root>/mutants-webapp/target.
	
4. Deploy *.war* file in a web server

4.a This can be done locally (with a Tomcat server for example)

    https://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html

4.b Also can be done in a cloud (heroku for a simple example)

Having a new app configured in heroku (less than 1 minute) we can run this command in the *<project-root>* to deploy it at heroku. 
	
	$ heroku war:deploy mutants-webapp/target/mutants-webapp.war --app NAME_OF_APP_IN_HEROKU
	


	

