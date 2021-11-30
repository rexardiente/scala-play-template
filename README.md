# Scala Play framework 2.8.x Template

This boiler template similar to a Maven archetype. This gives you the advantage using Model-View-Controller (MVC) architectural pattern that separates an application into three main logical components: the model, the view, and the controller.

The main purpose of this repository is to show a working Scala Play Framework, Database connection and workflow for setting up Play framework for development.

Check out the bootstrapping tools reference in [Play Framework Official Site](https://www.playframework.com/getting-started)

<img src="https://user-images.githubusercontent.com/10578315/143105110-5d664cf3-12f3-4bc2-b093-f1ea810a4b46.png" width="100%"/>

---
#### What you get in this template?
- Scala object with implicit JSON read and write
- Data access object (DAO) with DatabaseConfigProvider
- Sample CRUD API
- POST api with form validations
- Simple Secure Action
- SHA-256 password encryption
- Web socket client with play MessageFlowTransformer

### Table of Contents

- [Getting Started](#Getting-started)
- [Configure PostgreSQL Database](#Configure-PostgreSQL-Database)
- [Configure Play Slick with tminglei](#Configure-Play-Slick-with-tminglei)
- [Configure Play Evolution](#Configure-Play-Evolution)
- [Run Application](#Run-Application)
- [Running a production server in place](#Running-a-production-server-in-place)
- [Running a test instance](#Running-a-test-instance)
- [Automated Testing](#Automated-Testing)
- [License](#License)

### Prerequisites
___
A Play application only needs to include the Play JAR files to run properly. These JAR files are published to the Maven Repository, therefore you can use any Java or Scala build tool to build a Play project. However, Play provides an enhanced development experience (support for routes, templates compilation and auto-reloading) when using the sbt.

This project requires:

- Java SE 1.8 or higher
- sbt - we recommend the latest version
- PostgreSQL

#### Current Project Dependencies
- Play version 2.8.x
- Scala version >= 2.13.6
- sbt version 1.5.2

### Getting started
___
Clone the repository

    git clone https://github.com/rexardiente/scala-play-template.git

### Configure PostgreSQL Database
___
Setup PostgreSQL on macOS, keep in mind that yours might be different from this example.

Open terminal/command line and follow the process:

Create new user

    createuser db-user

Create database name

    createdb db-name

Sig in PSQL and update newly created user and give permissions

    // Giving the user a password
    Alter user "db-name" with encrypted password 'db-password;
    // Granting user privileges on database
    Grant all privileges on database "db-user" to "db-name";

### Configure Play Slick with tminglei
___
import the following libraries into `build.sbt`, in this template we are going to use `play-slick`, `play-slick-evolutions` and `slick-pg`.

    "com.typesafe.slick" %% "slick" % "3.3.3",
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    "com.github.tminglei" %% "slick-pg" % "0.19.7",
    "com.github.tminglei" %% "slick-pg_core" % "0.19.7",
    "com.github.tminglei" %% "slick-pg_play-json" % "0.19.7"

add this line of code into `application.conf`

    # Default DB - PostgreSQL
        slick.dbs {
          default.profile="models.utils.PostgresDriver$"
          default.db.driver="org.postgresql.Driver"
          default.db.url="jdbc:postgresql://127.0.0.1/db-name"
          default.db.user="db-user"
          default.db.password="db-pass"
          default.db.keepAliveConnection=true
    }

### Configure Play Evolution
___

__Evolutions scripts__

Play tracks your database evolutions using several evolutions script. These scripts are written in plain old SQL and should be located in the `conf/evolutions/{database name}` directory of your application. If the evolutions apply to your default database, this path is `conf/evolutions/default`.

The first script is named `1.sql`, the second script `2.sql`, and so on..
more information can be found [here](https://www.playframework.com/documentation/2.8.x/Evolutions).

__Play Module__ allows you to wire types to concrete implementation so that components provided by your module can be injected in users’ code, or in other modules.

__Evolution Script with Play Module__

Create `SchemaGenerationModule.scala` into package `models.utils` that will inherit from `play.api.inject.Module`, and provide an implementation for the bindings method.

__Test Schema Evolution__

By running the application it will generate new schema based on  `SchemaGenerator.scala` into `target/schema.sql`.

Copy the all the content inside `schema.sql` into `1.sql` filem, and reload the browser, it will prompt you to accept schema evolution and if you have changes DB schema in the future just repeat __Test Schema Evolution__ process.

___Note:__ Evolutions are automatically activated if a database is configured in `application.conf` and evolution scripts are present. You can disable them by setting `play.evolutions.enabled=false`._

### Run Application
___
___Note:__ -Duser.timezone=GMT command extension will enable application on running in UTC/GMT timezone._
Enter `sbt run` to download dependencies and start the system.
In a browser, enter http://localhost:9000 to view the welcome page.

    sbt run -Duser.timezone=GMT

### Running a production server in place
___
In some circumstances, you may not want to create a full distribution, you may in fact want to run your application from your project’s source directory. This requires an sbt installation on the server, and can be done using the stage task.

    sbt clean stage
    target/universal/stage/bin/scala-play-template -Duser.timezone=GMT

### Running a test instance
___
Play provides a convenient utility for running a test application in prod mode.

`Note:` This is not intended for production usage.

To run an application in prod mode, run runProd:

    sbt runProd -Duser.timezone=GMT

### Automated Testing
___
The location for tests is in the “test” folder.

You can run tests from the Play console.

To run all tests, run test.

To run only one test class, run test-only followed by the name of the class, i.e., test-only my.namespace.MySpec.

To run only the tests that have failed, run test-quick.

To run tests continually, run a command with a tilde in front, i.e. ~test-quick.

To access test helpers such as FakeRequest in console, run test:console.x

    sbt test

### License
___
Copyright (c) Rex Ardiente. All rights reserved. Licensed under the [MIT](https://github.com/rexardiente/scala-play-template/blob/main/LICENSE) License.