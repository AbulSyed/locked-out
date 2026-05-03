module "auth_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-auth-service"
  service = "auth"
  env     = "dev"
}

module "identity_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-identity-service"
  service = "identity"
  env     = "dev"
}

module "frontend_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-frontend"
  service = "frontend"
  env     = "dev"
}

module "vpc" {
  source                    = "./modules/vpc"
  vpc_name                  = "Locked Out VPC"
  vpc_cidr                  = "10.0.0.0/16"
  public_subnets            = local.public_subnets
  private_subnets           = local.private_subnets
  internet_gateway_name     = "Locked Out Internet Gateway"
  public_rt_destination_ip  = "0.0.0.0/0"
  public_rt_name            = "Locked Out Public Route Table"
  nat_eip_name              = "Locked Out NAT EIP"
  nat_gateway_name          = "Locked Out NAT GW"
  private_rt_destination_ip = "0.0.0.0/0"
  private_rt_name           = "Locked Out Private Route Table"
}

module "sg" {
  source                = "./modules/sg"
  alb_sg_name           = "Locked Out Load Balancer SG"
  vpc_id                = module.vpc.vpc_id
  ecs_sg_name           = "Locked Out ECS SG"
  auth_service_port     = 8080
  identity_service_port = 8081
  frontend_port         = 3000
}

resource "aws_ecs_cluster" "ecs_cluster" {
  name = "locked-out-ecs-cluster"
}

resource "aws_iam_role" "ecs_execution_role" {
  name = "lockedOutEcsTaskExecutionRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Service = "ecs-tasks.amazonaws.com"
      }
      Action = "sts:AssumeRole"
    }]
  })
}

# covers required ECR & CloudWatch permissions
resource "aws_iam_role_policy_attachment" "ecs_execution_role_policy" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

module "auth_cloudwatch" {
  source             = "./modules/cloudwatch"
  cluster_name       = aws_ecs_cluster.ecs_cluster.name
  service            = "auth"
  log_retention_days = 5
  env                = "dev"
}

module "auth_ecs" {
  source                  = "./modules/ecs"
  task_def_ecs_family     = "locked-out-auth-service-task-definition"
  task_def_cpu            = "256"
  task_def_memory         = "512"
  task_execution_role_arn = aws_iam_role.ecs_execution_role.arn
  service                 = "auth-service"
  ecr_url                 = module.auth_ecr.repository_url
  container_port          = 8080
  log_group_name          = module.auth_cloudwatch.log_group_name
  log_region              = "eu-west-2"

  cluster_id         = aws_ecs_cluster.ecs_cluster.id
  desired_count      = 0
  private_subnet_ids = module.vpc.private_subnet_ids
  ecs_sg_id          = module.sg.ecs_sg_id
}
