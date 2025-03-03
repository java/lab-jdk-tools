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

Congratulations! You finished the lab! 

## References

You can stay up-to-date with the latest changes with JDK tools by following:ls

* https://inside.java/tags
* https://dev.java/learn/