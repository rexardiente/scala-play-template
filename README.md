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
- Scala version >= 2.13.6
- sbt-plugin >= 2.8.8
- PostgreSQL


#### Play Slick with tminglei

import the following libraries into `build.sbt`

    "com.typesafe.slick" %% "slick" % "3.3.3",
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    "com.github.tminglei" %% "slick-pg" % "0.19.7",
    "com.github.tminglei" %% "slick-pg_core" % "0.19.7",
    "com.github.tminglei" %% "slick-pg_play-json" % "0.19.7"

add this line of code into `application.conf`

    # Default DB - PostgreSQL
		slick.dbs {
		  default.profile="utils.db.PostgresDriver$"
		  default.db.driver="org.postgresql.Driver"
		  default.db.url="jdbc:postgresql://127.0.0.1/db-name"
		  default.db.user="db-user"
		  default.db.password="db-pass"
		  default.db.keepAliveConnection=true
    }

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
