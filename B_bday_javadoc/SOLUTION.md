# Solutions to Generate Documentation with `javadoc`

## **Lab Activity No 2**: Introduce a Code Snippet in JavaDoc Comments

<details>
<summary>Click to expand</summary>

```java
package eu.ammbra.bday.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ammbra.bday.store.LocalDateTypeAdapter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Interface for loading JSON store from a file.
 *
 * @param <T> the type of store to load
 */
public interface JsonLoader<T> {

  /**
   * Loads JSON store from a file into a list of objects.
   *
   * Below is how the JSON file should look like.
   *
   * {@snippet lang = JSON:
   *		[
   *			{
   *				"id":1,
   *				"person":{            // @highlight region substring="person" type=highlighted
   *					"firstName":"Alice",
   *					"lastName":"Cooper",
   *					"date":"2025-03-15"
   *				},                    // @end
   *				"flavor":"Chocolate",
   *				"location":"Aventura Park",
   *				"attendees":100
   *			}
   *		]
   *}
   * @param filename the name of the file to load from
   * @param type     the type of store to load
   * @return an unmodifiable list of loaded objects
   * @throws IllegalArgumentException if an I/O error occurs while loading the file
   */
  default List<T> loadJson(String filename, Type type) {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    try (Reader reader = new FileReader(filename)) {
      List<T> json = gson.fromJson(reader, type);
      return Collections.unmodifiableList(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load celebrations from file: " + filename, e);
    }
  }
}
```

</details>

## **Lab Activity No 3**: Reference the Code Snippet from a File

<details>
<summary>Click to expand</summary>

```java
package eu.ammbra.bday.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ammbra.bday.store.LocalDateTypeAdapter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Interface for loading JSON store from a file.
 *
 * @param <T> the type of store to load
 */
public interface JsonLoader<T> {

  /**
   * Loads JSON store from a file into a list of objects.
   *
   * Below is how the JSON file should look like.
   *
   * {@snippet lang = JSON file = sample.json}
   *
   * @param filename the name of the file to load from
   * @param type     the type of store to load
   * @return an unmodifiable list of loaded objects
   * @throws IllegalArgumentException if an I/O error occurs while loading the file
   */
  default List<T> loadJson(String filename, Type type) {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    try (Reader reader = new FileReader(filename)) {
      List<T> json = gson.fromJson(reader, type);
      return Collections.unmodifiableList(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load celebrations from file: " + filename, e);
    }
  }
}
```

Enhanced command:

```shell
# Unix and macOS compatible command

javadoc -cp "../lib/*" -d docs -sourcepath src/main/java --snippet-path src/snippets -subpackages eu.ammbra.bday.store eu.ammbra.bday

# Windows compatible command

javadoc -cp "..\lib\*" -d docs -sourcepath src\main\java --snippet-path src\snippets -subpackages eu.ammbra.bday.store eu.ammbra.bday
```
Modified test method:

```java
	@Test
	public void testLoadJson_ValidFile() throws IOException {
		Path filePath = Path.of("./src/snippets/sample.json");

		JsonLoader<Celebration> loader = new JsonLoader<>() {};
		Type type = new TypeToken<List<Celebration>>() {}.getType();
		List<Celebration> result = loader.loadJson(filePath.toString(), type);

		assertEquals(1, result.size());
	}
```
</details>

## **Lab Activity No 4**: Utilize Markdown in JavaDoc Comments

<details>
<summary>Click to expand</summary>

```java
package eu.ammbra.bday.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ammbra.bday.store.LocalDateTypeAdapter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


///Interface for loading JSON store from a file.
///
///@param <T> the type of store to load
///
public interface JsonLoader<T> {

  ///Loads JSON store from a file into a list of objects.
  ///
  ///Below is how the JSON file should look like.
  ///
  ///{@snippet lang = JSON file = sample.json}
  ///
  ///@param filename the name of the file to load from
  ///@param type     the type of store to load
  ///@return an unmodifiable list of loaded objects
  ///@throws IllegalArgumentException if an I/O error occurs while loading the file
  ///
  default List<T> loadJson(String filename, Type type) {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    try (Reader reader = new FileReader(filename)) {
      List<T> json = gson.fromJson(reader, type);
      return Collections.unmodifiableList(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load celebrations from file: " + filename, e);
    }
  }
}
```

</details>

Next, [let's create a simple client-server setup](../C_bday_jwebserver/README.md)!
