# Web Development Testing with `jwebserver`

## Project Structure

Here's a breakdown of the different project components:

* `src/main/java`: The source code for the project, organized by package:
    * `eu.ammbra.bday.Organizer.java`: The main entry point of the application, possibly responsible for coordinating the overall flow of the program.
    * `eu.ammbra.bday.details`: A package containing domain model classes. These classes represent entities involved in birthday celebrations, such as cakes, parties, and people attending them.
    * `eu.ammbra.bday.handlers`: A package containing classes responsible for handling specific tasks. 
    * `eu.ammbra.bday.operations`: A package containing classes responsible for handling the operational side of parties.
    * `eu.ammbra.bday.store`: A package containing classes responsible for interacting with data from files.
* `src/main/resources/store/events.json`: A resource file containing sample event data in JSON format.
* `src/main/resources/static`: A folder holding client-side related static files.
* `src/test/java`: The source code for unit tests and integration tests.
* `src/snippets`: The JSON snippets folder.


## **Lab Activity No 1**: Simulate a client-server setup

JDK 18 introduced the `SimpleFileServer` API and added it to the `jdk.httpserver` module. This simple web server is a minimal HTTP static file server, designed to be used for prototyping, testing, and debugging.

You can run the simple web server in the command line with the `jwebserver` JDK tool.
[Jwebserver](https://docs.oracle.com/en/java/javase/23/docs/specs/man/jwebserver.html) serves static files in a single directory hierarchy over HTTP/1.1; dynamic content and other HTTP versions are not supported. 

Often times, front-end development occurs in parallel with back-end efforts. Imagine the following scenario:

* you work on a Java API but you need to know how your colleagues working on front-end expect data from your API
* in the same time, your colleagues develop HTML, CSS, javascript files and need to be aware on how your API endpoints and data served by them would look like.

To improve productivity of teams that work with different programming languages (Java, javascript),
would be great if both teams can access each others work without repetitive deployment. `jwebserver` can help you achieve this simple setup.

**Your task** relies on you to do the following steps:

1. Launch the `Organizer.java` application in a terminal window.
2. Open another terminal window and place yourself in `C_bday_jwebserver/src/main/resources/static` folder.
3. Launch `jwebserver` by running `jwebserver` command.
4. Visit the link `jwebserver` output suggests and check if it connects to your backend API.
5. While `jwebserver` still runs, modify the `C_bday_jwebserver/src/main/resources/static/index.html` file with:

```html
  <p>Explore details of party number 1 <a href=":8081/api/organize/1">/api/organize/1</a></p>
```
6. Refresh the browser window to see if the change was taken into account.

> ⚠️ If your terminal is unaware of `jwebserver`, you may invoke it through `$JAVA_HOME/bin/jwebserver`. All JDK tools are executable from JDK's `bin` folder.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-1-simulate-a-client-server-setup)

## **Lab Activity No 2**: Modify Server Code to Load Static Files

In addition to a command-line tool, the Simple Web Server provides an API for programmatic creation and customization of the server and its components. 
This API extends the `com.sun.net.httpserver` package. Moreover, the `HttpServer` API is capable of working with multiple handlers and sometimes
is possible to combine a file handler with a canned response handler

Let's evolve the previous scenario by integrating the client-side code. 
**Your task** relies on you to do the following steps:

1. Modify the code of `C_bday_jwebserver/src/main/java/eu/ammbra/bday/Organizer.java` to handle the content inside `static` folder.

```java
Path staticDir = Paths.get(Organizer.class.getResource("/static").toURI());
var fileHandler = SimpleFileServer.createFileHandler(staticDir);
//serve the static files 
server.createContext("/", fileHandler);

System.out.printf("Birthday Party Server is running at http://127.0.0.1:%d%n", port);
```
2. Launch the previously modified `Organizer.java` application in a terminal window. Now access the server running on http://127.0.0.1:8081.

## **Lab Activity No 3**: Create Integration Tests with HttpClient API

The `HttpClient` API was added in Java 11 and you can use it to request HTTP resources over the network.

```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://openjdk.org/"))
      .build();
client.send(request, HttpResponse.BodyHandlers.ofString());
```

In **this activity** you will leverage the `HttpClient` API to create integration tests for our setup:

1. Go to the `src/test/java/eu/ammbra/bday/OrganizerIntegrationTest.java` file.
2. The `setup()` and `tearDown()` methods are already prepared for you and those should not be changed.
3. Fix all the tests so they can assess correctly the application.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-3-create-integration-tests-with-httpclient-api)

Next, [let's create a minimal runtime for the application](../D_bday_jlink/README.md)!


