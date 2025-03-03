package eu.ammbra.bday.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalDateTypeAdapterTest {

	private LocalDateTypeAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new LocalDateTypeAdapter();
	}

	@Test
	public void testSerialize() {
		LocalDate date = LocalDate.of(2025, 1, 1);
		JsonElement result = adapter.serialize(date, LocalDate.class, null);
		assertEquals("2025-01-01", result.getAsString());
	}

	@Test
	public void testDeserializeValidDate() throws JsonParseException {
		String dateString = "2025-01-01";
		LocalDate result = adapter.deserialize(new JsonPrimitive(dateString), LocalDate.class, null);
		assertEquals(LocalDate.of(2025, 1, 1), result);
	}

	@Test
	public void testDeserializeInvalidDate() {
		String dateString = "invalid-date";
		assertThrows(DateTimeParseException.class, () -> adapter.deserialize(new JsonPrimitive(dateString), LocalDate.class, null));
	}

	@Test
	public void testDeserializeNullDate() {
		assertThrows(NullPointerException.class, () -> adapter.deserialize(new JsonPrimitive((String) null), LocalDate.class, null));
	}

	@Test
	public void testDeserializeNonStringDate() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("key", true);
		assertThrows(UnsupportedOperationException.class, () -> adapter.deserialize(jsonObject, LocalDate.class, null));
	}

	@AfterEach
	void tearDown() {
		adapter = null;
	}
}