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
  frontend_port         = 80
  rds_sg_name           = "Locked Out RDS SG"
}

resource "aws_ecs_cluster" "ecs_cluster" {
  name = "locked-out-ecs-cluster"
}

# Task Execution Role
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

resource "aws_iam_policy" "ecs_execution_role_policy" {
  name = "lockedOutEcsTaskExecutionRolePolicy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        "Effect" = "Allow",
        "Action" = [
          "ecr:GetAuthorizationToken",
          "ecr:BatchCheckLayerAvailability",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        "Resource" = "*"
      },
      {
        Effect = "Allow"
        Action = [
          "secretsmanager:GetSecretValue"
        ]
        Resource = "*"
      },
      {
        Effect = "Allow"
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath"
        ]
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_execution_role_policy" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = aws_iam_policy.ecs_execution_role_policy.arn
}

module "auth_cloudwatch" {
  source             = "./modules/cloudwatch"
  cluster_name       = aws_ecs_cluster.ecs_cluster.name
  service            = "auth"
  log_retention_days = 5
  env                = "dev"
}

module "identity_cloudwatch" {
  source             = "./modules/cloudwatch"
  cluster_name       = aws_ecs_cluster.ecs_cluster.name
  service            = "identity"
  log_retention_days = 5
  env                = "dev"
}

module "frontend_cloudwatch" {
  source             = "./modules/cloudwatch"
  cluster_name       = aws_ecs_cluster.ecs_cluster.name
  service            = "frontend"
  log_retention_days = 5
  env                = "dev"
}

module "auth_ecs" {
  source                  = "./modules/ecs"
  task_def_ecs_family     = "locked-out-auth-service-task-definition"
  task_def_cpu            = "256"
  task_def_memory         = "512"
  task_execution_role_arn = aws_iam_role.ecs_execution_role.arn
  task_role_arn           = null
  service                 = "auth-service"
  ecr_url                 = module.auth_ecr.repository_url
  container_port          = 8080

  environment_vars = []
  secret_vars      = []

  log_group_name = module.auth_cloudwatch.log_group_name
  log_region     = "eu-west-2"

  cluster_id         = aws_ecs_cluster.ecs_cluster.id
  desired_count      = 0
  private_subnet_ids = module.vpc.private_subnet_ids
  ecs_sg_id          = module.sg.ecs_sg_id

  target_group_arn      = module.auth_alb.target_group_arn
  alb_http_listener_arn = aws_lb_listener.default_http_listener.arn
}

module "identity_ecs" {
  source                  = "./modules/ecs"
  task_def_ecs_family     = "locked-out-identity-service-task-definition"
  task_def_cpu            = "256"
  task_def_memory         = "512"
  task_execution_role_arn = aws_iam_role.ecs_execution_role.arn
  task_role_arn           = null
  service                 = "identity-service"
  ecr_url                 = module.identity_ecr.repository_url
  container_port          = 8081

  environment_vars = [
    {
      name  = "POSTGRES_HOST"
      value = module.rds.postgres_host
    },
    {
      name  = "POSTGRES_USERNAME"
      value = data.aws_ssm_parameter.postgres_user.value
    }
  ]
  secret_vars = [
    {
      name      = "POSTGRES_PASSWORD"
      valueFrom = data.aws_secretsmanager_secret.postgres_secret.arn
    }
  ]

  log_group_name = module.identity_cloudwatch.log_group_name
  log_region     = "eu-west-2"

  cluster_id         = aws_ecs_cluster.ecs_cluster.id
  desired_count      = 0
  private_subnet_ids = module.vpc.private_subnet_ids
  ecs_sg_id          = module.sg.ecs_sg_id

  target_group_arn      = module.identity_alb.target_group_arn
  alb_http_listener_arn = aws_lb_listener.default_http_listener.arn
}

module "frontend_ecs" {
  source                  = "./modules/ecs"
  task_def_ecs_family     = "locked-out-frontend-service-task-definition"
  task_def_cpu            = "256"
  task_def_memory         = "512"
  task_execution_role_arn = aws_iam_role.ecs_execution_role.arn
  task_role_arn           = null
  service                 = "frontend-service"
  ecr_url                 = module.frontend_ecr.repository_url
  container_port          = 80

  environment_vars = []
  secret_vars = []

  log_group_name = module.frontend_cloudwatch.log_group_name
  log_region     = "eu-west-2"

  cluster_id         = aws_ecs_cluster.ecs_cluster.id
  desired_count      = 0
  private_subnet_ids = module.vpc.private_subnet_ids
  ecs_sg_id          = module.sg.ecs_sg_id

  target_group_arn      = module.frontend_alb.target_group_arn
  alb_http_listener_arn = aws_lb_listener.default_http_listener.arn
}

resource "aws_lb" "alb" {
  name               = "locked-out-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [module.sg.alb_sg_id]
  subnets            = module.vpc.public_subnet_ids

  # just for development for terraform destroy
  enable_deletion_protection = false

  tags = {
    name = "locked-out-lb"
  }
}

module "auth_alb" {
  source            = "./modules/alb"
  target_group_name = "locked-out-auth-tg"
  container_port    = 8080
  vpc_id            = module.vpc.vpc_id

  health_path = "/auth/health"

  listener_arn     = aws_lb_listener.default_http_listener.arn
  priority         = 100
  target_group_arn = module.auth_alb.target_group_arn
  path             = "/auth/*"
}

module "identity_alb" {
  source            = "./modules/alb"
  target_group_name = "locked-out-identity-tg"
  container_port    = 8081
  vpc_id            = module.vpc.vpc_id

  health_path = "/identity/health"

  listener_arn     = aws_lb_listener.default_http_listener.arn
  priority         = 101
  target_group_arn = module.identity_alb.target_group_arn
  path             = "/identity/*"
}

module "frontend_alb" {
  source            = "./modules/alb"
  target_group_name = "locked-out-frontend-tg"
  container_port    = 80
  vpc_id            = module.vpc.vpc_id

  health_path = "/health" # see frontend/nginx.conf

  listener_arn     = aws_lb_listener.default_http_listener.arn
  priority         = 102 # lowest priority
  target_group_arn = module.frontend_alb.target_group_arn
  path             = "/*"
}

resource "aws_lb_listener" "default_http_listener" {
  load_balancer_arn = aws_lb.alb.arn

  port     = 80
  protocol = "HTTP"

  default_action {
    type = "fixed-response"

    fixed_response {
      content_type = "text/plain"
      message_body = "Not Found"
      status_code  = "404"
    }
  }
}

module "parameters" {
  source         = "./modules/ssm"
  ssm_parameters = local.ssm_parameters
}

module "secrets" {
  source  = "./modules/secrets-manager"
  secrets = local.secrets
}

module "rds" {
  source               = "./modules/rds"
  db_subnet_group_name = "locked-out-db-subnet-group"
  private_subnet_ids   = module.vpc.private_subnet_ids
  db_identifier        = "locked-out-database"
  engine               = "postgres"
  engine_version       = "15"
  instance_class       = "db.t3.micro"
  allocated_storage    = 20
  db_name              = "locked_out"
  username             = data.aws_ssm_parameter.postgres_user.value
  password             = data.aws_secretsmanager_secret_version.postgres_secret_value.secret_string
  rds_sg_id            = module.sg.rds_sg_id
}
