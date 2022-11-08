# Introduction
As the junior developers will follow the Software Development Life Cycle to develop a Spring 
Boot-based trading platform that Jarvis Securities Team is planning to launch, we decided to 
master the concept of core Java. This Java grep app is capable of what the Linux `grep` command, 
which searches for a string in groups of files. Maven was used to manage this project and Docker 
was used to pack, deploy, and run the application with the container. For this project, we also 
used Lambda expressions to provide a concise way to represent one method interface using an 
expression, and to efficiently iterate through, filter, and extract data from a Collection. 
DRY (Don't Repeat Yourself) and KISS (Keep It Simple, Stupid) design principles were used for the 
best practice

# Quick Start
To get started with our project, you can follow the instruction below:
1. Pull a **docker image** from _docker hub_ using the command.
    ```
    docker pull heesoolim/grep:latest
    ```
2. Run the following command to check if the **image** is successfully pulled.
    ```
    docker image ls | grep "grep"
    ```
3. Run a new **docker container** using the image pulled from the _Docker hub_.
    ```
    docker run --rm \
    -v [HOST_MOUNT_SOURCE_ON]:[CONTAINER_MOUNT_POINT] \
    heesoolim/grep [YOUR_REGEX] [FOLDER_TO_SEARCH_PATH] [OUTPUT_FILE_PATH]
    ```
   * Following is an example of the above command:
   ```
   docker run --rm \
   -v `pwd`/data:/data -v `pwd`/log:/log \
   ${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out
   ```


# Implementation

## Pseudocode
- Find and get all the regular files under the specified source folder
- Get all the lines in each file
- Evaluate and filter the lines that contain matching regex that is passed in the argument
- Write the filtered line to the file which is specified in the argument list

## Performance Issue
Under an environment that does not have efficient heap memory to store every list of 
files and lines, `OutOfMemoryError` occurs due to the memory leak. To solve the problem, 
and improve the performance of the application, `BufferedReader` was used to read the data
a chunk at a time avoiding loading the entire file in memory, `Stream` was used instead of `List`
to not have storage for the `List` itself.

# Test
The application has been manually tested to ensure that it meets the requirements and functions as expected. 

Following are sample data and test cases:

**SAMPLE DATA**
```
PATH: /home/centos/Desktop/test_1
A long time ago there was a very beautiful girl named Cinderella.
Cinderella had long red hair, green eyes, and freckles all over her nose. She was clever and kind, and she loved to tell jokes.
But she was very unhappy. Her father and mother had died, and Cinderella lived with her stepmother and two stepsisters.
Although they all lived in a big house, they were actually quite poor. Their money was nearly gone.
```
```
PATH: /home/centos/Desktop/test_2
Cinderella's stepmother wanted one of her daughters to marry a rich man, so they would no longer be poor.
But Cinderella's stepsisters were not as pretty as Cinderella, not as kind as Cinderella, and not as funny as Cinderella.
The men who came to the house always fell immediately in love with Cinderella, and never noticed the stepsisters.
This frustrated the stepmother, so she ordered Cinderella to do all the chores.
```
```
PATH: /home/centos/Desktop/test_3
The stepmother tried hard to make Cinderella miserable.
The stepsisters had beautiful dresses and shoes, but Cinderella's dress was made of old rags.
The stepsisters always ate the most delicious foods, but Cinderella always ate scraps.
And the stepsisters slept in their comfortable beds in their bedrooms, but Cinderella slept on a straw bed on the kitchen floor.
```
**TEST CASE**
1. Find lines containing the word **love** and write to the file `grep.out`
   ```
   # command
   docker run --rm -v `pwd`:/home/centos/Desktop heesoolim/grep .*love.* ~/Desktop ~/Desktop/grep.out
   ```
   ```
   # result (~/Desktop/grep.out)
   Cinderella had long red hair, green eyes, and freckles all over 
   her nose. She was clever and kind, and she loved to tell jokes.
   The men who came to the house always fell immediately in love 
   with Cinderella, and never noticed the stepsisters.
   ```
2. Find lines starting with the word **Cinderella** and write to the file `cinderella.out`
   ```
   docker run --rm -v `pwd`:/home/centos/Desktop heesoolim/grep Cinderella.* ~/Desktop ~/Desktop/cinderella.out
   ```
   ```
   # result (~/Desktop/cinderella.out)
   Cinderella had long red hair, green eyes, and freckles all over her 
   nose. She was clever and kind, and she loved to tell jokes.
   Cinderella's stepmother wanted one of her daughters to marry a rich 
   man so they would no longer be poor.Cinderella had long red hair, 
   green eyes, and freckles all over her nose. She was clever and 
   kind, and she loved to tell jokes.Cinderella's stepmother wanted 
   one of her daughters to marry a rich man so they would no longer be poor.
   ```

# Deployment
Our application is **dockerized** for better and easier distribution. To build a **container image**,
we used `Dockerfile`, which is a text-based file without file extension containing a script 
of instructions that Docker uses to create a **container image**. Once the **container image** 
is created, we pushed the image to Docker Hub.

# Improvement
1. Allow the users to add options such as `-c` to print only the count of matching lines
2. Add a feature for searching multiple files
3. Allow the users to limit the number of lines in the output file