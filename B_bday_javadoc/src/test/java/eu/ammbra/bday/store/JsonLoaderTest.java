package eu.ammbra.bday.store;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import eu.ammbra.bday.details.Celebration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonLoaderTest {

	@TempDir
	Path tempDir;

	@Test
	public void testLoadJson_ValidFile() throws IOException {

		Path filePath = tempDir.resolve("valid-store.json");
		String validData = "[]";
		try (FileWriter writer = new FileWriter(filePath.toFile())) {
			writer.write(validData);
		}

		JsonLoader<Celebration> loader = new JsonLoader<>() {};
		Type type = new TypeToken<List<Celebration>>() {}.getType();
		List<Celebration> result = loader.loadJson(filePath.toString(), type);

		assertEquals(Collections.emptyList(), result);
	}

	@Test
	public void testLoadJson_InvalidFile() {
		Path filePath = tempDir.resolve("nonexistent.json");
		JsonLoader<String> loader = new JsonLoader<>() {};
		Type type = new TypeToken<List<String>>() {}.getType();
		assertThrows(IllegalArgumentException.class, () -> loader.loadJson(filePath.toString(), type));
	}

	@Test
	public void testLoadJson_MalformedJson() throws IOException {
		// Given
		Path filePath = tempDir.resolve("malformed-store.json");
		try (FileWriter writer = new FileWriter(filePath.toFile())) {
			writer.write("""
					[
					  {
					    "id": 1,
					    "name": "John"
					  }
					  {
					    "id": 2,
					    "name": "Jane"
					  }
					]
					""");
		}

		JsonLoader<String> loader = new JsonLoader<>() {};
		Type type = new TypeToken<List<String>>() {}.getType();
		assertThrows(JsonSyntaxException.class, () -> loader.loadJson(filePath.toString(), type));
	}
}