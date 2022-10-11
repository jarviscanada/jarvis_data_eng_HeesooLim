# Introduction
There are many cluster monitoring solutions produced by different companies, and this project will also monitor the performance of the cluster entities. 

For this project, there will be a **cluster of 10 nodes** which are all running **CentOS 7**, which are managed by *Jarvis Linux Cluster Administration*, and each of the nodes is linked to the switch internally enabling them to communicate with each other through IPv4 addresses. 
Following is what our project does to provide a proper solution to the Jarvis team:
- Record the **hardware information** and the usage of the **memory** and **CPU** in RDBMS.
- Allow the Jarvis team to better plan the resources in the future utilizing the generated data by our project.

In this project, we use **CentOS** as our server, **Bash scripts** to get the information required and insert it into **PostgreSQL**, and **Docker** to run our database.

As a branching model of this project, we use *GitFlow*, so we can achieve the best practice of continuous delivery.

# Quick Start
1. Start/Stop/Create a psql instance using psql_docker.sh script:
    ```
   # Start a psql instance
   ./scripts/psql_docker.sh start
   
   # Stop a psql instance
   ./scripts/psql_docker.sh stop
   
   # Create a psql instance
   ./scripts/psql_docker.sh create [db_username] [db_password]
    ```
2. Create tables using ddl.sql
    ```
    psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
    ```
3. Insert hardware specs data into the DB using host_info.sh
   ```
   ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
   
   # Example
   ./scripts/host_info.sh localhost 5432 host_agent postgres password
   ```
4. Insert hardware usage data into the DB using host_usage.sh
   ```
   bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
   
   # Example
   bash scripts/host_usage.sh localhost 5432 host_agent postgres password
   ```
5. Crontab setup
   1. Run `crontab -e` command to make change to `crontab`.
   2. Add the following to the file
   ```
   * * * * * bash PATH_TO_host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
   ```
***To check if crontab works, connect to the psql instance by using `psql -h HOST_NAME -p 5432 -U USER_NAME` and run `SELECT * FROM host_usage`.***
# Implementation

[//]: # (Implementation goes here.)

[//]: # (Discuss how you implement the project.)
As the LCA team needs to record the hardware information of each server and 
monitor the usage of resources such as CPU and memory in real time, 
we collect and store the information in RDBMS every minute using `crontab`. 

To implement the project, we used the following:
- Linux command lines
- Bash scripts
- PostgreSQL
- Docker

**Some of the commands that we frequently used to collect information are**
```
# Fetch CPU-related information from the sysfs files
lscpu

# Display information about processes, memory, block IO, disks and cpu activity.
vmstat

# Get the Fully Qualified Domain Name(FQDN)
hostname -f
```

**To create, stop, and start the docker instance, we created a bash script `psql_docker.sh`.**

**To store the hardware information, we implemented`host_info.sh`.**

**To collect the resource usage information, we implemented `host_usage.sh`, 
and it will be executed every minute.**
## Architecture
![](/home/centos/dev/jarvis_data_eng_heesoo/linux_sql/assets/architecture.jpg)
Each host has two scripts(agent):
1. **host_info.sh**: It's triggerred once at the beginning to insert the **hardware information** of the machine into the database
2. **host_usage.sh**: It's executed every minute to record **CPU** and **memory** of the machine by using `cron`
## Scripts
- `psql_docker.sh`: create/stop/start psql docker instance

   Some commands used:
   ```
  # Start the Docker service if it's not running
  sudo systemctl status docker || systemctl start docker
  
  # Get the status of the container
  docker container inspect jrvs-psql
  
  # Start or stop the container
  docker container start/stop jrvs-psql
   ```
   
- `host_info.sh`: collect and store the host's hardware information. (executed only once)
   
   Some commands used:
   ```
   lscpu         # CPU-related information
   vmstat        # Process, memory and disk information
   hostname -f   # Fully Qualified Domain Name
   grep          # Search plain text data sets
   awk           # Process and manipulate text
   xargs         # Trim white space
   ```
- `host_usage.sh`: collect and store the host's resource usage. (executed every minute by `crontab`)
- `crontab`: schedule a job for a specific time. In our case, it runs `host_usage.sh` every minute.

## Database Modeling
Schemas used for the host_info and host_usage table
- `host_info`

| column           | type      | constraint       |
|------------------|-----------|------------------|
| id               | Integer   | Primary key      |
| hostname         | Varchar   | Unique, Not null |
| cpu_number       | Integer   | Not null         |
| cpu_architecture | Varchar   | Not null         |
| cpu_model        | Varchar   | Not null         |
| cpu_mhz          | Numeric   | Not null         |
| l2_cache         | Integer   | Not null         |
| total_mem        | Integer   | Not null         |
| timestamp        | Timestamp | Not null         |

- `host_usage`

| column         | type       | constraint                 |
|----------------|------------|----------------------------|
| timestamp      | Timestamp  | Not null                   |
| host_id        | Integer    | Foreign key: host_info(id) |
| memory_free    | Integer    | Not null                   |
| cpu_idle       | Integer    | Not null                   |
| cpu_kernel     | Integer    | Not null                   |
| disk_io        | Integer    | Not null                   |
| disk_available | Integer    | Not null                   |

# Test
- **Test the `psql_docker.sh`**: 

    after executing the script with/without arguments,
    you can run the command `docker ps -a` to check if the docker instance has been created, started or stopped.
- **Test the `host_usage.sh`**:
    
    run the command from the shell and check if there is a matching record
    ```
    psql -h localhost -U postgres -d host_agent -c "SELECT * FROM host_info WHERE hostname='YOUR_HOST_NAME'"
    ```
- **Test the `host_info.sh`**

    Run the command from the shell and check if a new usage information is added
    ```
    psql -h localhost -U postgres -d host_agent -c "SELECT * FROM host_info i JOIN host_usage u ON i.id=u.host_id WHERE hostname='YOUR_HOST_NAME'"
    ```
- **Test the `ddl.sql`**:

    Run `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql` 
then `\l` to see if the table `host_info` and `host_usage` are created. 
# Deployment
**GitHub** is used as a version control tool for this project. 
We used **crontab** to collect the host resource usage data regularly, 
and **Docker** to provision our psql instance.

# Improvements
- Create a separate error log file for the `host_usage.sh` script
- Allow additional arguments to `psql_docker.sh` script to start and stop multiple different containers
- Create an automated test script for testing all other scripts.