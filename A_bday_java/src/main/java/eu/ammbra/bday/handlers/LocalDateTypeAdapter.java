package eu.ammbra.bday.handlers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

 /// Custom adapter for serializing and deserializing {@link LocalDate} objects to/from JSON strings.
 ///
 /// This adapter uses the ISO date format `yyyy-MM-dd` for serialization and deserialization.
 ///
public class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

	 /// Formatter used for parsing and formatting dates.
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	 /// Serializes a {@link LocalDate} object to a JSON string.
	 ///
	 /// @param date       the date to serialize
	 /// @param srcType    the type of the source object
	 /// @param context    the serialization context
	 /// @return a JSON primitive representing the serialized date
	 ///
	@Override
	public JsonElement serialize(final LocalDate date, final Type srcType,
								 final JsonSerializationContext context) {
		return new JsonPrimitive(date.format(formatter));
	}

	 /// Deserializes a JSON string to a {@link LocalDate} object.
	 ///
	 /// @param json      the JSON element to deserialize
	 /// @param targetType   the type of the target object
	 /// @param context   the deserialization context
	 /// @return the deserialized date
	 /// @throws JsonParseException if the JSON element cannot be parsed
	 ///
	 @Override
	public LocalDate deserialize(final JsonElement json, final Type targetType,
								 final JsonDeserializationContext context) throws JsonParseException {
		return LocalDate.parse(json.getAsString(), formatter);
	}
}