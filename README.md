JDK Tools Lab
============================

## Introduction

This lab explores the latest features of the JDK tools and how to combine them in development:

1. Understand the latest features **java** launcher
2. Use **javadoc** tool and its type of comments
3. Leverage **jwebserver** tool and `SimpleFileServer` API
4. Analyze dependencies with **jdeps** and produce minimal runtimes with **jlink**.
5. Utilize **jfr** commands to monitor your application.

Each part is implemented in its own module, so that you can follow one or the other, independently.

## Prerequisites 

* JDK 23 minimum installed and configured. If you do not have it installed, please obtain one from https://jdk.java.net/23/
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
```

Each module contains its own README.md file, with the instructions and hints on how to work on the different parts of the lab. They are all independent, so you can choose the one you want to work on.
The project was generated with IntelliJ's build system, but below are additional steps in case you decide to use a different code editor.

In case you wish to work in Eclipse IDE, please use [eclipse](https://github.com/java/lab-jdk-tools/blob/eclipse/README.md) branch instructions.

Now [let's get started](A_bday_java)!