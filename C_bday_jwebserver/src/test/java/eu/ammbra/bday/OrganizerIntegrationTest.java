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
        HttpResponse<String> response = client.send(null, null);

        assertEquals(404, response.statusCode());
		assertTrue(response.body().contains("<h1>File not found</h1>"));
    }

	@Test
	public void testApiNotFoundEvent() throws IOException, InterruptedException {
		HttpResponse<String> response = client.send(null, null);

		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("Event with id 3 is not found"));
	}

	@Test
	void testApiEndpoint() throws IOException, InterruptedException {
		HttpResponse<String> response = client.send(null, null);
		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("Aventura Park"));
	}

    @Test
	public void testStaticFileServing() throws IOException, InterruptedException {
		HttpResponse<String> response = client.send(null, null);

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("<html lang=\"en\">"));
    }

	@AfterEach
	void tearDown() {
		Organizer.stop();
	}
}