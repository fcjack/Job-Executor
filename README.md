# Job Executor
Job Executor is a simple project that have the simple task to schedule
some job and execute.
The scheduling of jobs is not on time, but is putting on Queue of requests
that will be processed by a ThreadPoolExecutor.

The _ThreadPoolExecutor_ starts with the size of 10 threads to execute and
the API has a URL to request the resize of this ThreadPool.

If the new size is lower than the actual size, the size will update
when the threads finish the job that were executing and become idle state.

## Swagger
The project has a swagger configured to show the REST API that is available on project.
After start the project we can access the Swagger-UI using the following URL, for example: http://localhost:8080/job-executor/swagger-ui.html

## Application Design
The application is composed for three modules:
* Common
* Plugins
* Job Executor

The common project is the project that have the common entities and interfaces for projects.
The base structure of models and parameters are on this project.

The Plugins is a project that will contain Custom Workers that can be utilized on executor.
If I need some new Worker, I can implement on plugin without change code inside JobExecutor.
The same happen if I need fix a Worker that is on Plugin Projects.
I will need generate a new build for Job Executor because Plugins project is a dependency
of this project.

The JobExecutor is the Web application with Spring Boot that has the API and the Swagger.
This Project is the main module that will need Common and Plugins project as dependencies and
this project has the implementation of some basic Workers too. 

## Jobs
The current state of the project allow some tasks, that have the model of a _JobType_
and the _Parameters_ when necessary.

## Job Types
The Job Types allowed are:
* Word counter (JSON parameter -> WORD_COUNTER)
* Spark Word Counter (JSON parameter -> SPARK_WORD_COUNTER)
* Failure (JSON parameter -> FAILURE)
* Calculate Pi (JSON parameter -> CALCULATE_PI)
* Wait for 20 seconds (JSON parameter -> WAIT_FOR_20_SECONDS)

#### Parameters
The task Word Counter and Spark Word Counter need one parameter that is
the path to the file with the text that will be processed.
The result of the process will be saved on file on same directory,
if was executed by Spark will create a new directory inside File Directory
with the result.

## Schedule
To schedule a task is just send a POST request for endpoint _task/schedule_
with the model on body as in the example below.

```
    {
        "jobType": "WORD_COUNTER",
        "parameters": {
            "inputFilePath": "/home/files/t8.shakespeare.txt"
        }
    }
```

If the required field is not sent the response will be a Bad Request with
a message telling that the field is required.

## Next steps

* As improvements with the project we need to store the results of the tasks inside 
a repository or database. At the moment tasks to calculate values will be displayed on log, but not stored.
* Implements more Workers for project.
* Create metrics to monitoring the status of the application.
* Cancel one task on Queue.

