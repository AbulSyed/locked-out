# ECS Cluster resource & task execution role defined in main.tf

resource "aws_ecs_task_definition" "auth_service" {
  family                   = var.task_def_ecs_family
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"

  cpu    = var.task_def_cpu
  memory = var.task_def_memory

  execution_role_arn = var.task_execution_role_arn
  # task_role_arn      = aws_iam_role.ecs_task_role.arn

  container_definitions = jsonencode([
    {
      name  = var.service
      image = "${var.ecr_url}:latest"

      portMappings = [
        {
          containerPort = var.container_port
          protocol      = "tcp"
        }
      ]

      essential = true

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.log_group_name
          awslogs-region        = var.log_region
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

resource "aws_ecs_service" "auth_ecs_service" {
  name            = var.service
  cluster         = var.cluster_id
  task_definition = aws_ecs_task_definition.auth_service.arn

  desired_count = var.desired_count
  launch_type   = "FARGATE"

  network_configuration {
    subnets         = var.private_subnet_ids
    security_groups = [var.ecs_sg_id]
  }
}
