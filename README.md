JDK Tools Lab
============================

## Introduction

This lab explores the latest features of the JDK tools and how to combine them in development:

1. Understand the latest features of the **java** launcher.
2. Use **javadoc** tool and its type of comments.
3. Leverage **jwebserver** tool and `SimpleFileServer` API
4. Analyze dependencies with **jdeps** and produce minimal runtimes with **jlink**.
5. Utilize **jfr** commands to monitor your application.

Each part is implemented in its own module, so that you can follow one or the other, independently.

## Prerequisites 

* JDK 25 minimum installed and configured. If you do not have it installed, please obtain one from https://jdk.java.net/23/
and follow JDK setup steps from here https://dev.java/learn/getting-started/#setting-up-jdk. Check your Java version setup with:

```shell
java -version 
```

* Use your favorite IDE (like [IntelliJ](https://dev.java/learn/intellij-idea/) or [Eclipse](https://dev.java/learn/eclipse/)) or a code editor like [VS Code](https://dev.java/learn/vscode-java/).
* (Optional for `jlink` integration step) Docker CLI running on your machine. Check that by running the following command in a terminal window:

```shell
docker version 
```

## Working on the Lab

Start by cloning the code from this repo:

```shell
git clone https://github.com/java/lab-jdk-tools.git
git checkout eclipse
```

Each module contains its own `README.md` file, with the instructions and hints on how to work on the different parts of the lab. They are all independent, so you can choose the one you want to work on.
The project was generated with IntelliJ's build system, but below are additional steps in case you decide to use a different code editor.

### Eclipse IDE Setup
<details>
<summary>Click to expand</summary>

#### Import as a General Java Project

1. Open Eclipse.
2. Go to _File > New > Java Project_.
3. Enter the same project name (`lab-jdk-tools`).
4. Uncheck **"Use default location"** and browse to the `lab-jdk-tools` project folder.
5. Click Next and Finish. 

To fix missing libraries in each module follow these steps: 
1. Right-click the project and select Properties_.
2. Go to _Java Build Path > Libraries_.
3. Click **"Add External JARs..."** and select JARs from the _lib/_ folder.
4. Click Apply and Close. 

Make sure that a _src/resources/_ folder from a module is recognized:

1. Right-click _resources/ > Build Path > Use as Source Folder_.

#### Verify and Run the Project in Eclipse

Once you imported the project: 

1. Check _Source Folders → src/main/java_ and _src/resources/_ are correctly recognized.
2. Check Dependencies → JAR files from lib/ should be in the **Java Build Path**.

#### Configure Eclipse Output Folder (Optional)

By default, Eclipse compiles classes into _bin/_, but IntelliJ uses _out/_. To unify this:

1. Right-click the _module > Properties > Java Build Path_.
2. Go to **"Source"** tab and change **"Default output folder"** to out/production/<module_name>.
3. Click Apply and Close.

</details>

Now [let's get started](A_bday_java)!
