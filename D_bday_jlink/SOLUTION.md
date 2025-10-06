# Solutions to Create Minimal Runtimes with `jlink`

## **Lab Activity No 1**: Analyze the Project's Dependencies with `jdeps`

<details>
<summary>Click to expand</summary>

After running:
```shell
# unix/macOS specific command
jdeps --class-path '../lib/*' --ignore-missing-deps --print-module-deps --multi-release 25 out/

# Windows specific commands
jdeps --class-path '..\lib\*' --ignore-missing-deps --print-module-deps --multi-release 25 out/
```

the output should be:

```shell
java.base,java.net.http,java.sql,jdk.httpserver
```
</details>

## **Lab Activity No 2**: Generate a Minimal Java Runtime

<details>
<summary>Click to expand</summary>

Final `jlink` command should look like:

```shell
jlink --add-modules java.base,java.net.http,java.sql,jdk.httpserver --no-man-pages --no-header-files --compress=zip-9 --output javaruntime
```
</details>

## **Lab Activity No 3**: Assemble a Dockerfile with the Minimal Java Runtime


<details>
<summary>Click to expand</summary>

```dockerfile
# Define your base image
FROM container-registry.oracle.com/java/openjdk:25-oraclelinux9 as jre-build

RUN $JAVA_HOME/bin/jlink \
--add-modules jdk.compiler,java.base,java.net.http,java.sql,jdk.httpserver \
--no-man-pages \
--no-header-files \
--compress=zip-9 \
--output javaruntime

# Define your base image
FROM oraclelinux:9-slim

ENV JAVA_HOME /usr/java/openjdk-25
ENV PATH $JAVA_HOME/bin:$PATH
COPY --from=jre-build /javaruntime $JAVA_HOME

WORKDIR app

# Continue with your application deployment
COPY ./lib /app/lib
COPY ./D_bday_jlink/src/main /app

RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

ENV JDK_JAVA_OPTIONS "--enable-preview"

ENTRYPOINT java --class-path 'lib/*' java/eu/ammbra/bday/Organizer.java "./resources/store/events.json" 8081
```
</details>

## **Lab Activity No 4**: Separate Deployments of Frontend and Backend

<details>
<summary>Click to expand</summary>

The final `Dockerfile.web` looks as following:

```dockerfile
# Step 1: Build the custom Java runtime
FROM container-registry.oracle.com/java/openjdk:25-oraclelinux9 as jre-build

RUN $JAVA_HOME/bin/jlink \
--add-modules jdk.compiler,jdk.httpserver \
--no-man-pages \
--no-header-files \
--compress=zip-9 \
--output javaruntime

# Step 2: Create the runtime environment
FROM oraclelinux:9-slim

# Set Java environment variables
ENV JAVA_HOME /usr/java/openjdk-25
ENV PATH $JAVA_HOME/bin:$PATH

# Copy the custom Java runtime from the first stage
COPY --from=jre-build /javaruntime $JAVA_HOME

# Set the working directory
WORKDIR /web

# Copy static files into the container
COPY ./static /web

# Create a non-root user and switch to it
RUN groupadd -r appuser && useradd -r -g appuser appuser && chown -R appuser:appuser /web
USER appuser

# Expose the HTTP port
EXPOSE 8002

# Start jwebserver with proper settings
ENTRYPOINT jwebserver --port 8002 --directory /web -b 0.0.0.0
```
</details>

Next, [let's analyze the performance of the application](../E_bday_jfr/README.md)!
