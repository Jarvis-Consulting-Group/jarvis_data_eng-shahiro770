# Introduction
The linux cluster monitoring agent is a light-weight tool designed to get hardware metrics for the machine it is running on in real time, sending the collected data periodically back to an RDBMS to be persisted. The primary users of this tool, the Jarvis Linux Cluster Administration (LCA) team, intend to take the compiled data to generate reports for future resource planning of nodes in the cluster (scale up/down server units horizontally/vertically, cost planning, etc.). The technologies used for building this tool include Bash, Docker, PostgreSQL, Crontab, and Git.

# Quick Start
Assuming docker is installed on the host node, first run the script required to launch PSQL docker container with the dependencies necessary for the RDBMS and to set up the corresponding credentials. Specifically, the `db_username` and `db_password` fields will be the credentials for the PSQL instance.
```console
./scripts/psql_docker.sh create psql_username psql_password
./scripts/psql_docker.sh start 
```
For the machine hosting the database (which will be the same as the one with the docker container created just now), setup the PSQL database.
```
psql -h localhost -U postgres -W
CREATE DATABASE host_agent;
```
Now create the tables for where the hardware metrics will be stored.
```console
psql -h hostname -U postgres -d host_agent -f ./sql/ddl.sql
```
With all the RDBMS setup done, all thats left is the agent-related work. Any node that plans to have its metrics recorded must first persist its system information to the database.
```
./scripts/host_info.sh psql_hostname psql_port host_agent psql_user psql_password
```
Now with the static system metrics recorded, the script for getting real-time usage data can be used. To run the script manually on its own, use `host_usage.sh`.
```
./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
To automate the script with crontab such that it records metrics every minute, open up your crontab's config with `crontab -e` and save the following command inside.
```
* * * * * bash ../path_to_repo_from_root/linux_sql/scripts/host_usage.sh hostname port host_agent psql_user psql_password > /tmp/host_usage.log
```

# Implemenation
Majority of the project was done via Bash scripts. In short, the project's design can be broken down into three parts:
* A Bash script to initialize the docker container for the RDBMS's dependencies (i.e. postgres:9.6-alpine)
* PostgreSQL as the RDBMS for storing all collected data (with `ddl.sql` serving as the setup script for defining the schema)
* Bash scripts serving as the monitoring agents for collecting a machine's data and running the SQL necessary for storage, with `host_info.sh` being run once initially to grab static metrics, and `host_usage.sh` being run at one-minute intervals via Crontab

## Architecture
![image](./assets/Cluster%20Diagram.png)
## Scripts
### psql_docker.sh
Used for creating the docker container to run the postgreSQL instance, as well as starting/stopping it. It will not re-create the container if it already exists.
```
./psql_docker.sh create|start|stop db_username db_password 
```
### host_info.sh
Calls linux commands such as lscpu, vmstat, and hostname and greps for specific hardware data attributes that are presumed to be static. Afterwards, it writes the data to the PSQL database in the table host_info.
```
./host_info.sh hostname port db_name db_username db_password
```
### host_usage.sh
Similar to host_info.sh, except it collects a different subset of hardware data attributes that fluctuate with the machine's usage. The data is written to the PSQL database in the table host_usage.
```
./host_usage.sh hostname port db_name db_username db_password
```
### crontab
A job scheduling utility in Linux/Unix systems that can be used as a form of automation for tasks as directed by the user or other processes. In this case, the calling of the host_usage.sh script is specified as a task to be performed by the cron daemon every minute.
```console
# open the crontab config
cront -e

# inside the crontab config
* * * * * bash ../path_to_repo_from_root/linux_sql/scripts/host_usage.sh hostname port host_agent psql_user psql_password > /tmp/host_usage.log
```
## Database Modeling
### host_info
| Attribute        | Data Type | Description                               | Constraint  |
|------------------|-----------|-------------------------------------------|-------------|
| id               | SERIAL    | Assigned numeric identifier to the system | Primary Key |
| hostname         | VARCHAR   | Fully qualified hostname                  | Unique      |
| cpu_number       | INT2      | Number of CPU cores                       | Not Null    |
| cpu_architecture | VARCHAR   | Type of CPU architecture                  | Not Null    |
| cpu_model        | VARCHAR   | Type of CPU model                         | Not Null    |
| cpu_mhz          | FLOAT8    | CPU frequency in MHz                      | Not Null    |
| l2_cache         | INT4      | Size of L2 cache in KB                    | Not Null    |
| "timestamp"      | TIMESTAMP | Time of metric recording (UTC)            | Not Null    |
| total_mem        | INT4      | Total memory in KB                        | Not Null    |
### host_usage
| Attribute      | Data Type | Description                                              | Constraint                               |
|----------------|-----------|----------------------------------------------------------|------------------------------------------|
| "timestamp"    | TIMESTAMP | Time of metric recording (UTC)                           | Not Null                                 |
| host_id        | SERIAL    | Foreign key for assigned identification number           | Foreign Key (references id in host_info) |
| memory_free    | INT4      | Amount of free memory in MB                              | Not Null                                 |
| cpu_idle       | INT2      | Percentage of time the CPU has spent idle                | Not Null                                 |
| cpu_kernel     | INT2      | Percentage of time the CPU has spent running kernel code | Not Null                                 |
| disk_io        | INT4      | Number of disk I/O operations in progress                | Not Null                                 |
| disk_available | INT4      | Amount of free disk space in MB                          | Not Null                                 |

# Test
As it was designed under the swift guidelines as an MVP, the project testing was handled in a simplified manner. Using a single CentOS 7 VM as a sandbox, the scripts for setting up the PSQL database and collecting host hardware info and usage were ran. Metric recording was done over the course of a 2 day long period, with values being periodically verified via the Linux CLI.

Going forward, proper unit and integration tests will be implemented after a verification by the LCA team confirms all requirements have been satisfied.

# Deployment
For the fast timeline of the project, lightweight deployment tools were utilized in favour of speed and agility. GitHub was used for hosting the app files so that anyone can easily download, run, and/or modify the scripts as they see fit. Additionally, a docker container must be created on the RDBMS hosting node for storage. The agent script for collecting real time hardware usage data `host_usage.sh` is automated via Crontab.

# Improvements
Given that this service was designed as a MVP, there are many convenience features that were missed in design that would greatly benefit stakeholders for longterm usage. Some of these include:

- Proper unit and integration testing, ideally mocking the structure of a linux cluster to confirm any bottleneck for data collection is primarily the network architecture and not any of the scripts involved
- Further automation of the setup process for the node hosting the RDBMS, ideally making the container and PSQL setup a single command
- Additional scripts for querying specific metrics from the database