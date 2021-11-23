# Scala Play framework 2.8.x Template

Scala play framework boiler template similar to a Maven archetype. This gives you the advantage using Model-View-Controller (MVC) architectural pattern that separates an application into three main logical components: the model, the view, and the controller.

---
#### This project includes the ff.

- Scala class/object with implicit JSON read and write
- Data access object (DAO) with DatabaseConfigProvider
- CRUD API
- Form Validations
- Secure Action
- SHA-256 password encryption
- Web socket client with play MessageFlowTransformer

#### TODO

- Play with React frontend
- Play to Docker container

#### Required Dependencies

- Play version 2.8.x
- Java version >= 15.0.1
- Scala version >= 2.13.6
- sbt-plugin >= 2.8.8
- PostgreSQL

#### Setup PostgreSQL Database
Setup PostgreSQL on macOS, keep in mind that yours might be different from this example.

Open terminal/command line then type the ff.

__Create new User__

    createuser db-user

__Create database name__

    createdb db-name

__Update newly created PSQL user and give permissions__

    // Giving the user a password
    Alter user "db-name" with encrypted password 'db-password;
    // Granting user privileges on database
    Grant all privileges on database "db-user" to "db-name";

#### Play Slick with tminglei

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

#### Play Evolution
Create `PostgresDriver.scala` into package `models.utils`, example is in the source code

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

#### Run Application
    sbt run

#### Running a production server in place
In some circumstances, you may not want to create a full distribution, you may in fact want to run your application from your project’s source directory. This requires an sbt installation on the server, and can be done using the stage task.

    sbt clean stage

#### Running a test instance
Play provides a convenient utility for running a test application in prod mode.

`Note:` This is not intended for production usage.

To run an application in prod mode, run runProd:

    [scala-play-template] $ runProd

#### Automated Testing

    [scala-play-template] $ test

---
For more information about Play Framework visit [official documentations.](https://www.playframework.com/documentation/2.8.x/Deploying)
