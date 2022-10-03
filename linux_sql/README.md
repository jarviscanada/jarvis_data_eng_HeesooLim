# Introduction
There are many cluster monitoring solutions produced by different companies, and this project will also monitor the performance of the cluster entities. 

For this project, there will be a **cluster of 10 nodes** which are all running **CentOS 7**, which are managed by *Jarvis Linux Cluster Administration*, and each of the nodes is linked to the switch internally enabling them to communicate with each other through IPv4 addresses. 
Following is what our project does to provide a proper solution to the Jarvis team:
- Record the **hardware information** and the usage of the **memory** and **CPU** in RDBMS.
- Allow the Jarvis team to better plan the resources in the future utilizing the generated data by our project.

In this project, we use **CentOS** as our server, **Bash scripts** to get the information required and insert it into **PostgreSQL**, and **Docker** to run our database.

As a branching model of this project, we use *GitFlow*, so we can achieve the best practice of continuous delivery.

# Quick Start
Quick start guide goes here.
# Implemenation
Implementation goes here.
## Architecture
![](/home/centos/dev/jarvis_data_eng_heesoo/linux_sql/assets/architecture.jpg)
Each host has two scripts(agent):
1. **host_info.sh**: It's trigerred once at the beginning to insert the **hardware information** of the machine into the database
2. **host_usage.sh**: It's executed every minute to record **CPU** and **memory** of the machine by using `cron`