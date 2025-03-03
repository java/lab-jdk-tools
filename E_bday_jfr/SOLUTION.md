# Solutions to Monitor a Running Application

## **Lab Activity No 1**: Inspect Application Behavior without Dumping Data

<details>
<summary>Click to expand solution to point 8</summary>

```shell
jcmd PID JFR.view thread-cpu-load
```
</details>


<details>
<summary>Click to expand solution to point 9</summary>

```shell
jcmd PID JFR.view memory-leaks-by-class
```
</details>

<details>
<summary>Click to expand to point 10</summary>

```shell
jcmd PID JFR.view gc-pauses
```

</details>

<details>
<summary>Click to expand to point 11</summary>

```shell
jcmd PID JFR.view hot-methods
```

</details>

## **Lab Activity No 3**: Control JFR Recording in a Containerized Application

Let's investigate how you can record application behavior from an already running container.

1. Open a terminal window at the root of the `lab-jdk-tools` folder, where the [`Dockerfile`](../Dockerfile) resides.
2. Modify the `Dockerfile` by adding two more modules `jdk.jcmd,jdk.jfr` to the `jlink` command. Your application needs these modules in order to perceive `jcmd` or `jfr` commands.
3. Launch application via the command `docker-compose up --build` from the `lab-jdk-tools` folder.
4. The container that corresponds `Organizer` application has the name `organizer` (see `` in `compose.yaml`). Start a jfr recording that lasts 60 seconds for this containerized application:

```shell
docker exec -d -it organizer sh -c "jcmd 1 JFR.start duration=60s filename=../recordings/recording.jfr"
```
5. Once the recording has finished, you can inspect the file with the commands you learned in previous activities.

Congratulations! You finished the lab! 

## References

You can stay up-to-date with the latest changes with JDK tools by following:ls

* https://inside.java/tags
* https://dev.java/learn/