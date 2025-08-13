# Monitor a Running Application

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

At any time, you can search or look on [https://dev.java/learn/jvm/jfr/](https://dev.java/learn/jvm/jfr/) to learn more about JDK Flight Recorder.

## **Lab Activity No 1**: Inspect Application Behavior without Dumping Data

> ⚠️ If your terminal is unaware of `jfr`, you may invoke it through `$JAVA_HOME/bin/jfr`. All JDK tools are executable from JDK's `bin` folder.

JDK 21 came with a new JFR `view` command that displays aggregated event data in the shell.
To use the command, you have a few options:

*  when launching an application, use `-XX:StartFlightRecording` to start JFR a recording and invoke `jfr view <view> <filename>`,
*  start a JFR recording with `jcmd` and invoke `jfr view <view> <filename>`,
*  or start a JFR recording with `jcmd` and invoke `jcmd PID JFR.view <view>`.

To find out all options of `jfr view` command use the following command in a terminal window:

```shell
jfr view --help
```
**Your task** is to analyze a few aspects of the `Organizer` application using `jcmd` and `jfr view`.

1. Open another terminal window and place yourself in `E_bday_jfr` folder.
2. Inside the terminal, execute the `java` command to launch `Organizer` class from `src/main/java/eu/ammbra/bday/`.

```shell

# Unix and macOS compatible command

java --enable-preview -cp "../lib/*" src/main/java/eu/ammbra/bday/Organizer.java

# Windows compatible command

java --enable-preview -cp "..\lib\*" src\main\java\eu\ammbra\bday\Organizer.java

```

3. You will need to find the process ID of you JVM and for this you need to invoke `jps`. Search for `SourceLauncher`.
4. Save the ID of your application somewhere as you will need to copy it at the next step.
5. Start a jfr recording.

```shell
jcmd PID JFR.start
```
6. Access the URL of your application a couple of times.
7. An application can experience slow performance due to excessive thread contention. The `jfr view contention-by-thread <file>`
   can help identify which **threads are blocked** most of the time, but is also useful for optimizing **lock usage** or **synchronization mechanisms**.
   Replace the PID with yours and run the following command to inspect behavior without dumping data:

```shell
jcmd PID JFR.view contention-by-thread
```

8. If the application is consuming excessive **CPU resources**, `jfr view thread-cpu-load <file>` helps monitor **which threads** use the most CPU.
   Replace the PID with yours and run the `jcmd PID JFR.view` command to inspect behavior without dumping data.

9. When the application exhibits an **increasing memory footprint**, `jfr view memory-leaks-by-class` identifies **which classes are leaking memory**.
   Replace the PID with yours and run the `jcmd PID JFR.view` command to detect if there are any memory leaks.

10. If the application has **high GC pause times**, this can cause performance degradation. `jfr view gc-pauses` helps determine if frequent **GC pauses** are affecting application responsiveness.
    Replace the PID with yours and run the `jcmd PID JFR.view` command to inspect GC pause times.

11. In order to find which **methods consume the most execution time**, use `jfr view hot-methods`.
    Replace the PID with yours and run the `jcmd PID JFR.view` command to check which methods consume the most execution time.

Next, let's see how you can filter recorded data in a file.

## Solution

&rarr; [Click to see the solution](SOLUTION.md#lab-activity-no-1-inspect-application-behavior-without-dumping-data)

## **Lab Activity No 2**: Filter Data from a Recording File

> ⚠️ If your terminal is unaware of `jfr`, you may invoke it through `$JAVA_HOME/bin/jfr`. All JDK tools are executable from JDK's `bin` folder.

The `jfr scrub` command is useful to remove sensitive information from JFR recordings. When sharing JFR files is good to ensure that personally identifiable information (PII), security-sensitive data, or environment-specific details are removed.
In JDK 25, the `jfr scrub` command also prints the number of events that were removed, making it easier to verify the amount of sensitive information has been taken out.

```shell
jfr scrub [filters] [recording-file] [output-file]
```
**This last activity** needs you to do the following steps:

1. Start with dumping the JFR recording you started earlier:

```shell
jcmd PID JFR.dump name=1 filename=record.jfr
```

2. Next, let's scrub the events that contain `Environment` in their name.

```shell
jfr scrub --exclude-events "jdk.*Environment*"  record.jfr
```
The output is written to the file `record-scrubbed.jfr`.
3. Now, let's further clean the scrubbed recording by excluding the `Network` category:

```shell
jfr scrub --exclude-categories "Network"  record-scrubbed.jfr 
```

4. If you want to keep events related to reading files but hide execution details, run the following command:

```shell
jfr scrub --include-events "FileRead" --exclude-threads '*metadata*' record-scrubbed.jfr
```

5. Print the events from `record-scrubbed.jfr` and see if the recording is clean according to commands given previously.
   As of JDK 25, you can find out timestamps, timespans, and memory data with full precision by appending  `--exact` option to your `jfr print command`.

```shell
jfr print --events record-scrubbed.jfr
```

6. Finally, stop the JFR recording by executing:

```shell
jcmd PID JFR.stop name=1
```

## **Lab Activity No 3**: How the New JFR Cooperative Sampling Works

Java 25 improves the stability of the JDK Flight Recorder (JFR) by changing how it samples Java thread stacks for profiling. Previously, JFR used asynchronous stack sampling at arbitrary code locations (non-safepoints), relying on heuristics to reconstruct stack traces.
These heuristics were complex and could sometimes cause JVM crashes, especially during class unloading. To eliminate this risk, starting with JDK 25, JFR samples stacks only at safepoints, where reliable metadata exists.

Furthermore, to avoid safepoint bias (e.g., when intrinsics replace Java code), which can miss frequently executed code between safepoints, JFR adopts a cooperative sampling model.
When sampling is triggered, the sampler thread records only minimal information (program counter and stack pointer), queues a sample request, and lets the target thread handle stack trace reconstruction at its next safepoint. This ensures both safe and accurate sampling without relying on risky heuristics.
A new `jdk.SafepointLatency` event has been added to JFR, enabling fine-grained visibility into how long threads take to reach safepoints.

**Your task** involves enabling `jdk.SafepointLatency` event when you launch your application and keep the recording alive for 1 minute.

```shell
java -XX:StartFlightRecording:jdk.SafepointLatency#enabled=true,filename=recording.jfr
```

Once the recording stopped, use `jfr print` command to check the duration of `jdk.SafepointLatency` events captured in the recording file.

## **Lab Activity No 4**: Timing and Tracing Method Invocations

Timing and tracing method invocations can help identify performance bottlenecks, optimize code, and find the root causes of bugs.
Starting with JDK 25, you can use two JFR events to time and trace method invocations:

* `jdk.MethodTiming` that tracks invocation count and execution time (min/avg/max) for specified methods.
* `jdk.MethodTrace` that records stack traces and duration for each invocation.

For each of these JFR events you can specify filters for methods, classes, or annotations using command-line arguments, JFR configuration files, or remotely via JMX.

```shell
jcmd PID JFR.start method-timing=com.sun.net.httpserver.HttpHandler::handle;java.io.FileDescriptor::close

java -XX:StartFlightRecording:jdk.MethodTrace#filter=com.sun.net.httpserver.HttpHandler::handle,filename=rec.jfr
```
Additionally, Java 25 enhances `-XX:StartFlightRecording` option with a new sub-option `report-on-exit` that prints a report/view when the JVM exits.

Let's checkout how you can use these new events for timing and tracing method invocations for `organizer` application.
**Your task** consists of going through the following steps:

1. Use `jfr configure  --interactive` command to create your custom JFR profile.
2. Search for `jdk.MethodTrace` and `jdk.MethodTiming` events and check if they are enabled.
3. Launch `Organizer` application from `src/main/java/eu/ammbra/bday/` and using the custom profile you just created.

```shell
java -XX:StartFlightRecording:settings=custom.jfc,duration=60s,filename=rec.jfr,report-on-exit=method-timing 
```
4. Make a few calls to the API in order to sample some events in your recording file.
5. Once the recording ends, inspect the file with `jfr print` or `jfr view all-events` commands.

## **Lab Activity No 5**: Control JFR Recording in a Containerized Application

JDK 25 introduces experimental support for CPU-time profiling in JDK Flight Recorder (JFR) on Linux using the Linux kernel’s CPU timer.
This enhances profiling accuracy by sampling threads based on actual CPU usage rather, overcoming limitations of JFR’s current execution-time sampler, which may miss threads in native code or underrepresent short runs.

The new event `jdk.CPUTimeSample` is not enabled by default and captures stack traces of Java threads at regular CPU-time intervals, ensuring better accuracy.

```shell
jfr configure jdk.CPUTimeSample#enabled=true
```

Let's investigate how you can record application behavior from an already running container.

1. Open a terminal window at the root of the `lab-jdk-tools` folder, where the [`Dockerfile`](../Dockerfile) resides.
2. Modify the `Dockerfile` by adding two more modules `jdk.jcmd,jdk.jfr` to the `jlink` command. Your application needs these modules in order to perceive `jcmd` or `jfr` commands.
3. Launch the application via the command `docker-compose up --build` from the `lab-jdk-tools` folder.
4. The container that corresponds to `Organizer` application has the name `organizer` (see `container_name` in `compose.yaml`).
5. Start a jfr recording that lasts 60 seconds for this containerized application:

```shell
docker exec -d -it organizer sh -c "jcmd 1 JFR.start settings=custom.jfc duration=60s filename=../recordings/recording.jfr"
```
6. Once the recording has finished, you can inspect the file with the commands you learned in previous activities.

Congratulations! You finished the lab!

## References

You can stay up-to-date with the latest changes with JDK tools by following:ls

* https://inside.java/tags
* https://dev.java/learn/
