# Walmart - Reminder Service

The Reminder service project allows users to:
	Add a reminder
	Update a reminder
	View reminders by Due Date and/or Status.  Leaving Due Date and Status blank will return all reminders.

## Getting Started

Pull down this project and issue a "mvn clean install" to build WalmartProject.war in /target.
Of course WalmartProject.war was uploaded in the event you don't want to have to build it yourself.
Deploy to servlet container.  I used Tomcat 7.

### Prerequisites

A servlet container.  I used Tomcat 7.
IDE if you want to look at and build the code.  (I used Eclipse)
Or you can build on the command line using Maven (I used 3.3.9)

### Installing

Pull down WalmartProject.war and deploy to servlet container, or pull down project and build and deploy.

### Running the project

Once WalmartProject.war has been deployed go to "http://localhost:8080/WalmartProject/" to display the Reminder Service UI.  Add, Update or Display Reminders.


## Running the tests

No tests.

## Deployment

Deploy WalmartProject.war to servlet container.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [H2](http://www.h2database.com/) - Used for in memory database

## Author

* **Ruben Roberts**

## Acknowledgments

* index.html uses javascript tabs from Elated Communications Ltd. (www.elated.com)
