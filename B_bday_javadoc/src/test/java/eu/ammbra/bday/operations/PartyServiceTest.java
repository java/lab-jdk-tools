package eu.ammbra.bday.operations;

import eu.ammbra.bday.store.DataStore;
import eu.ammbra.bday.details.Party;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PartyServiceTest {
	@TempDir
	Path tempDir;

	private PartyService partyService;


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
		DataStore dataStore = new DataStore(filePath.toString());
		partyService = new PartyService(dataStore);
	}

	@Test
	public void testProcessAllParties() {
		List<Party> result = partyService.processAllParties();

		assertEquals(1, result.size());
		Party party = result.getFirst();
		assertEquals(1L, party.id());
		assertEquals("Alice", party.name());
		assertEquals("Aventura Park", party.location());
		assertEquals(100, party.attendees());
	}

	@Test
	public void testProcessPartyById_Found() throws PartyNotFoundException {
		Party result = partyService.processPartyById(1L);

		// Then
		assertEquals(1L, result.id());
		assertEquals("Alice", result.name());
		assertEquals("Aventura Park", result.location());
		assertEquals(100, result.attendees());
	}

	@Test
	public void testProcessPartyById_NotFound() {
		assertThrows(PartyNotFoundException.class, () -> partyService.processPartyById(4L));
	}
}