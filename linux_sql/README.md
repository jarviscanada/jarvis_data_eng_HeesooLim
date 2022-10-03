# Introduction
There are many cluster monitoring solutions produced by different companies, and this project will also monitor the performance of the cluster entities. 

For this project, there will be a **cluster of 10 nodes** which are all running **CentOS 7**, which are managed by *Jarvis Linux Cluster Administration*, and each of the nodes is linked to the switch internally enabling them to communicate with each other through IPv4 addresses. 
Following is what our project does to provide a proper solution to the Jarvis team:
- Record the **hardware information** and the usage of the **memory** and **CPU** in RDBMS.
- Allow the Jarvis team to better plan the resources in the future utilizing the generated data by our project.

In this project, we use **CentOS** as our server, **Bash scripts** to get the information required and insert it into **PostgreSQL**, and **Docker** to run our database.

As a branching model of this project, we use *GitFlow* so we can achieve the best practice of continuous delivery.
