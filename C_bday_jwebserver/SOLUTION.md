# Solutions for Web Development Testing with `jwebserver`

## **Lab Activity No 1**: Simulate a client-server setup

<details>
<summary>Click to expand</summary>

Commands to launch the `Organizer.java` app from `C_bday_jwebserver/src/main/java/eu/ammbra/bday/Organizer.java` and `jwebserver`:

```shell

# Unix and macOS compatible commands

java --enable-preview -cp "../lib/*" src/main/java/eu/ammbra/bday/Organizer.java

$JAVA_HOME/bin/jwebserver

# Windows compatible commands

java --enable-preview -cp "..\lib\*" src\main\java\eu\ammbra\bday\Organizer.java

$JAVA_HOME\bin\jwebserver
```

</details>

## **Lab Activity No 3**: Create Integration Tests with HttpClient API


<details>
<summary>Click to expand</summary>

```java
package eu.ammbra.bday;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizerIntegrationTest {
  private static final int PORT = 9000;
  private static final String BASE_URL = "http://127.0.0.1:" + PORT;
  private HttpClient client;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setup() throws Exception {
    Path filePath = tempDir.resolve("valid-store.json");
    String validData = """
            [
              {
                "id":1,
                "person":{
                  "firstName":"Alice",
                  "lastName":"Cooper",
                  "date":"2025-03-15"
                },
                "flavor":"Chocolate",
                "location":"Aventura Park",
                "attendees":100
              }
            ]
            """;
    try (FileWriter writer = new FileWriter(filePath.toFile())) {
      writer.write(validData);
    }

    String[] args = new String[]{filePath.toString(), String.valueOf(PORT)};
    Organizer.main(args);

    client = HttpClient.newHttpClient();
  }

  @Test
  public void testApiInvalidEndpoint() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/org"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(404, response.statusCode());
    assertTrue(response.body().contains("<h1>File not found</h1>"));
  }

  @Test
  public void testApiNotFoundEvent() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/organize/3"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(200, response.statusCode());
    assertTrue(response.body().contains("Event with id 3 is not found"));
  }

  @Test
  void testApiEndpoint() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/organize"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(200, response.statusCode());
    assertTrue(response.body().contains("Aventura Park"));
  }

  @Test
  public void testStaticFileServing() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(200, response.statusCode());
    assertTrue(response.body().contains("<html lang=\"en\">"));
  }

  @AfterEach
  void tearDown() {
    Organizer.stop();
  }
}
```

</details>

Next, [let's create a minimal runtime for the application](../D_bday_jlink/README.md)!

