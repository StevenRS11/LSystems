# AGENTS Instructions for Codex

This repository contains a small Java project using LWJGL 2.9.0. There is no build automation or test suite. The following instructions describe how to set up the container and compile the project.

## Setup
1. Ensure OpenJDK is available. If it is not already installed run:
   ```bash
   apt-get update
   apt-get install -y openjdk-8-jdk
   ```

## Build
Compile the sources with the provided LWJGL jars:
```bash
mkdir -p bin
find src -name "*.java" > sources.txt
javac --release 8 -cp "libs/*" -d bin @sources.txt
```

## Run
Execute the example program:
```bash
java -cp "libs/*:bin" logic.LSystem
```

## Tests
There are no automated tests. Successful compilation is considered a passing check.
