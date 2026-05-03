## Infra

Folder to manage AWS infrastructure using terraform

### Download terraform

`tfswitch` is a useful cli tool for downloading & switching between different terraform versions.

### Terraform state file / Remote backend

Terraform state file is stored in AWS S3. Since state files contains sensitive info it should never be stored in version control systems. Also, S3 provides state locking which prevent concurrent modifications.

- `bootstrap-backend.tf` - creation of S3 with versioning, encryption & blocking public access.
- `backend.tf` - configuring terraform to use S3 as remote backend

### ECR

Docker images are stored in AWS ECR.

### GitHub OIDC

GitHub OIDC has been configured to allow GitHub Actions to authenticate with AWS to prevent the storing of secrets in GitHub. With OIDC GitHub requests a short-lived identity token from AWS, AWS verifies it and issues temporary credentials. So no stored credentials, leaked secrets & rotation required.

To configure GitHub OIDC:

In `iam.tf`:
- configured GitHub as a trusted identity provider in AWS
- created IAM role GitHub can assume
- created IAM policy for ECR push and attached to IAM role 

### AWS Network

#### VPC

Isolated virtual network in AWS where your compute resources live.

#### CIDR block

Defines a range of IP addresses your network can support for your resources. Everything inside your VPC - like EC2, ECS, RDS gets an IP from this range.

#### Subnets

Section of VPC which organises public and private services. Public subnets can be reached from outside like load balancers. Private subnets can’t be accessed from outside the network e.g. ECS.

#### Internet Gateway

Internet Gateway allows inbound & outbound traffic between VPC and the internet for resources in subnets that are routed and allowed by security groups.

#### NAT Gateway

Allows resources in a private subnet to access the internet.

#### Route tables

Used to direct traffic flow.

#### Security group

Firewall that controls inbound & outbound traffic by restricting to specific protocols, ports, and sources or destinations.

- Ingress - Incoming traffic
- Egress - Outgoing traffic

#### ECS

Container orchestration service that allows you to deploy, run & scale docker containers. It uses task definitions to define containers, tasks to run them, and services to maintain the desired number of running containers.

##### Task role

IAM role which defines permissions your application needs to access AWS services e.g. RDS, SQS.

##### Task execution role

IAM role which defines permissions ECS needs to start your container e.g. pulling image from ECR, sending logs to CloudWatch, fetching secrets from secrets manager & fetching parameters from parameter store.

##### Task

Represents a single running instance of a Task Definition.

##### Service

Ensures a specified number of tasks are always running. If 1 task crashes ECS automatically starts another.
