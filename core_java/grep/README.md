# Introduction
The Java Grep App re-creates the linux command `grep`, allowing for a user to find all lines contained in files under a directory that match a given regex pattern. The operation is done recusrively, hence it will search sub-directories as well. As the name implies, the app is made with Java, using Maven as its build management system to streamline development. The project makes use of many standard java libraries, such as java.io for I/O related reading/writing, and Java's Regex API to handle the pattern matching. Stream API and Lambdas were also used in the implementation to make reading in large data not as memory intensive. The app was developed on IntelliJ IDEA and deployed to DockerHub.

# Quick Start
To use the app, first pull the docker image.

```bash
docker pull shahiro777770/grep
```

From there, create a container from the docker image to perform the grep operation with the following syntax:

```bash
docker run --v [sourceVolume] --v [destinationVolume] shahiro777770/grep [regexPattern] [sourceDirectory] [destinationDirectory]
```

For the volumes, you may want to keep them in the same directory as the docker image. If so, an easy trick using `pwd` can be done to specify the argument (i.e. `pwd`/sourceVolume:/sourceVolume).

# Implemenation
## Pseudocode
The main driving method `process` runs as follows:
1. First it gets all of the files in the specified directory, as well as all subdirectories
2. For each of those files, if it is a parasable file, the program will view each of its lines
3. If the currently viewed line matches the regex pattern, it gets added to the list of lines to be written to the output file
4. After the above steps have all been completed, the output file with the matching lines is written to the specified destination directory

## Performance Issue
Depending on the JVM's maximum heap memory allocation, an OutOfMemoryException may be thrown when a sufficiently large file is attempting to be parsed. This risk can be alleviated by either setting the JVM's minimum and maximum heap memory large enough, or utilizing streams and buffered reading classes to prevent the heap from trying to handle excessive amounts of data.

# Test
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)
The application was tested manually through testing various regex patterns on sample data. Various text files were tried with both the stream-less and stream API implementations of the grep processing code, verifying that the program would correctly grab all matching lines. Additional tests were done to confirm the application would recursively go down the sub-directories in the root directory for files, as well as ignore parsing through non-file files.

# Deployment
A Docker image containing the compiled fat JAR with the necessary dependencies was deployed to DockerHub for ease of access and modification. The docker image file in specific used openjdk:8-alpine as its base image. From there we copied the contents of the compiled JAR file, such that they would exist in a specific directory when the container would be run, specifying that jar file as the entry point and for it to be executed via `java -jar` when the container starts up.

# Improvement
While the app gets the job done, it has room for improvement. A few changes I would consider for future development:
1. Add proper unit testing, using a well known testing frameworks such as JUnit4 or Mockito.
2. Add additional logging to allow for easier debugging from user-reports. While there isn't much scope of this project actually troubleshooting I/O failures, it would be worthwhile to at least know when and where they occur during execution, if at all.
3. Add additional functionality in letting the user choose between the more memory efficienct stream API implementation and the stream-less one. While there isn't much advantage in not using the stream API version, systems with older JVMs may not be compatible.