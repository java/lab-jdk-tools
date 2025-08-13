# Generate Documentation with `javadoc`

## Project Structure

Here's a breakdown of the different project components:

* `src/main/java`: The source code for the project, organized by package:
  * `eu.ammbra.bday.Organizer.java`: The main entry point of the application, possibly responsible for coordinating the overall flow of the program.
  * `eu.ammbra.bday.details`: A package containing domain model classes. These classes represent entities involved in birthday celebrations, such as cakes, parties, and people attending them.
  * `eu.ammbra.bday.handlers`: A package containing classes responsible for handling specific tasks.
  * `eu.ammbra.bday.operations`: A package containing classes responsible for handling the operational side of parties.
  * `eu.ammbra.bday.store`: A package containing classes responsible for interacting with data from files.
* `src/main/resources/store/events.json`: A resource file containing sample event data in JSON format.
* `src/test/java`: The source code for unit tests and integration tests.
* `src/snippets`: The JSON snippets folder.

## **Lab Activity No 1**: Highlight a Code Fragment

Traditionally, you can include formatted multiple line fragments of source code in documentation using `<pre>` and `{@code ...}`.
`<pre>` is the default HTML tag for preformatted text, meaning that HTML renders by default knowing that the code within the tag should be displayed literally.
Moreover, a code snippet embedded within `{@code}` displays the special characters correctly so they don’t need to be manually escaped.


```java
/**
 * <pre>
 *  {@code 
 *		[
 *			{
 *				"id":1,
 *				"person":{
 *					"firstName":"Alice",
 *					"lastName":"Cooper",
 *					"date":"2025-03-15"
 *				},
 *				"flavor":"Chocolate",
 *				"location":"Aventura Park",
 *				"attendees":100
 *			}
 *		]
 *	}
 *</pre>	
 */
```
While the code snippet is correctly formatted, it lacks syntax highlighting.
Starting with JDK 25, you can use the new `--syntax-highlight` option for `javadoc` to enable code highlighting in `{@snippet}` tags and `<pre>{@code ...}` elements.
The option doesn't need any arguments and adds the `Highlight.js` library to the generated documentation in a configuration that supports `Java`, `Properties`, `JSON`, `HTML` and `XML` code fragments.

**Your task** requires you to do the following steps:

1. Add `<pre>{@code ...}` elements with a code example above the `loadJson` method from [`lab-jdk-tools/B_bday_javadoc/src/main/java/eu/ammbra/bday/store/JsonLoader.java`](src/main/java/eu/ammbra/bday/store/JsonLoader.java) file.
2. For the code example, you can inspire yourself from [`lab-jdk-tools/B_bday_javadoc/src/snippets/sample.json`](src/snippets/sample.json).
3. Generate documentation using `javadoc` and enable also syntax highlighting.

## **Lab Activity No 2**: Introduce a Code Snippet in JavaDoc Comments

Since JDK 18 you can simplify the way you add code examples in API documentation by utilizing [snippets](https://docs.oracle.com/en/java/javase/23/javadoc/snippets.html).
You can use `{@snippet ...}` to enclose a fragment of text, such as source code or any other form of structured text.

```java
/**
 * {@snippet :
 *   public static void main(String... args) {    // @highlight region substring="text" type=highlighted
 *      var text = "";                            // @replace substring='""' replacement=" ... "
 *      System.out.println(text);
 *   }                                            // @end
 * }
 */
```
To highlight all or part of a line in a snippet, use the `@highlight` tag. In the example above, the empty string is used as a placeholder value, and the `@replace` tag specifies that it should be replaced with an ellipsis.

The markup comments in the previous example only affected the content on the same line. However, sometimes you may want tot affect the content on a range of lines, or region.
Regions can be anonymous or named. To have a markup tag apply to an anonymous region, place it at the start of the region and use an `@end` tag to mark the end of the region.

**Your task** relies on you to do the following steps:

1. Add a snippet tag on the `loadJson` method from [`lab-jdk-tools/B_bday_javadoc/src/main/java/eu/ammbra/bday/store/JsonLoader.java`](src/main/java/eu/ammbra/bday/store/JsonLoader.java) file.
2. This snippet tag should contain the json from [`lab-jdk-tools/B_bday_javadoc/src/snippets/sample.json`](src/snippets/sample.json).
3. Optionally, you can try to add a region to highlight parts of the JSON.
4. To validate your work, run `javadoc` command in a terminal window:

```shell
# Unix and macOS compatible command

javadoc -cp "../lib/*" -d docs -sourcepath src/main/java -subpackages eu.ammbra.bday.store eu.ammbra.bday

# Windows compatible command

javadoc -cp "..\lib\*" -d docs -sourcepath src\main\java -subpackages eu.ammbra.bday.store eu.ammbra.bday

```

Now go to `docs` folder and inspect the generated documentation.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-1-introduce-a-code-snippet-in-javadoc-comments)


## **Lab Activity No 3**: Reference the Code Snippet from a File

It is not always convenient to use inline snippets. Moreover, the Standard Doclet does not compile or otherwise test snippets; instead, it supports the ability of external tools and library code to test them.

**Your task** relies on you to do the following steps:

1. Modify the snippet tag on the `loadJson` method to load the json content from [`src/snippets/sample.json`](src/snippets/sample.json).
2. Modify the previous `javadoc` command to reference external snippets (`--snippet-path src/snippets`).
3. Run `javadoc` in a terminal window.
4. Now go to `docs` folder and inspect the generated documentation.
5. Modify `testLoadJson_ValidFile` method from `JsonLoaderTest.java` to test the content from `src/snippets/sample.json`.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-2-reference-the-code-snippet-from-a-file)

## **Lab Activity No 4**: Utilize Markdown in JavaDoc Comments

As of JDK 23, you can use [Markdown in Javadoc comments](https://docs.oracle.com/en/java/javase/23/javadoc/using-markdown-documentation-comments.html).

This activity relies on you to execute the following steps:

1. Go to the `src/main/java/eu/ammbra/bday/store/JsonLoader.java` file.
2. Change its JavaDoc comments to use Markdown.
3. Check if the previous `javadoc` command still works. If it doesn't work, find the appropriate option to fix it.
4. Go to `docs` folder and inspect the generated documentation.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-3-utilize-markdown-in-javadoc-comments)

Next, [let's create a simple client-server setup](../C_bday_jwebserver/README.md)!

