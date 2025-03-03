package eu.ammbra.bday.store;

import eu.ammbra.bday.details.Celebration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataStoreTest {

	@TempDir
	Path tempDir;

	private DataStore dataStore;

	@Test
	public void testLoadCelebrations_InvalidFile() {
		// Given
		Path filePath = tempDir.resolve("invalid-celebrations.json");

		// When and Then
		assertThrows(IllegalArgumentException.class, () -> new DataStore(filePath.toString()));
	}


	@Nested
	public class NestedValidDataStoreTest {

		@BeforeEach
		void setUp() throws IOException {
			Path filePath = tempDir.resolve("valid-celebrations.json");
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
			dataStore = new DataStore(filePath.toString());
		}


		@Test
		public void testLoadCelebrations_ValidFile() {
			List<Celebration> celebrations = dataStore.getCelebrations();
			assertEquals(1, celebrations.size());
			Celebration celebration = celebrations.getFirst();
			assertEquals(1L, celebration.id());
			assertEquals("Alice", celebration.person().firstName());
			assertEquals(LocalDate.of(2025, 3, 15), celebration.person().date());
			assertEquals("Aventura Park", celebration.location());
			assertEquals(100, celebration.attendees());
		}


		@Test
		public void testGetCelebrationById_Found() {
			Optional<Celebration> result = dataStore.getCelebrationById(1L);

			assertFalse(result.isEmpty());
			assertEquals(1L, result.get().id());
		}

		@Test
		public void testGetCelebrationById_NotFound() {
			Optional<Celebration> result = dataStore.getCelebrationById(2L);
			assertEquals(Optional.empty(), result);
		}
	}

}