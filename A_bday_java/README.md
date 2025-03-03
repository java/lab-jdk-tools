# Working with the Java Launcher

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

## **Lab Activity No 1**:  Launch Multiple Java Files

Since JDK 22 you can use the Java launcher to [execute source-code programs provided as multiple files](https://docs.oracle.com/en/java/javase/23/docs/specs/man/java.html#using-source-file-mode-to-launch-source-code-programs).

```shell
java --class-path '*' Prog1.java
```
This activity relies on you to execute the following steps:

1. Open a terminal window in the `lab-jdk-tools/A_bday_java` folder. You can use the terminal of your IDE/code editor.
2. Check that $JAVA_HOME is properly set by trying `echo $JAVA_HOME`.
3. Inside the terminal, execute the `java` command to launch `Organizer` class from `src/main/java/eu/ammbra/bday/`.
   Pay attention to the following:

* classpath libraries located in `lab-jdk-tools/lib` folder.
* the project uses preview features so do not forget to enable those via `--enable-preview`

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-1-launch-multiple-java-files)

## **Lab Activity No 2**: Use the Java Command-Line Argument Files

When working with the `java` launcher, you can shorten or simplify the `java` command by using `@` argument files to specify one or more text files that contain arguments.
The arguments can be options and class names, which are then passed to the `java` command. [This](https://docs.oracle.com/en/java/javase/23/docs/specs/man/java.html#java-command-line-argument-files) allows you to :

* create `java` commands of any length on any operating system
* for containerized applications, easily swap the content the argument files without modifying the Dockerfile.

This activity requires you to:

1. Create a file and copy the arguments you used to launch the `Organizer` class from `src/main/java/eu/ammbra/bday/`.
2. Now launch again the `Organizer` class by providing the file as argument `java @your-arg-file-name`.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-2-use-the-java-command-line-argument-files)

Next step is to [create some documentation](../B_bday_javadoc/README.md)!