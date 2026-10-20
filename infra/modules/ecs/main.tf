# ECS Cluster resource & task execution role defined in main.tf

resource "aws_ecs_task_definition" "task_definition" {
  family                   = var.task_def_ecs_family
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"

  cpu    = var.task_def_cpu
  memory = var.task_def_memory

  execution_role_arn = var.task_execution_role_arn
  task_role_arn      = var.task_role_arn

  container_definitions = jsonencode([
    {
      name  = var.service
      image = "${var.ecr_url}:latest"

      portMappings = [
        {
          name          = "http"
          containerPort = var.container_port
          protocol      = "tcp"
        }
      ]

      healthCheck = var.health_path != null ? {
        command = [
          "CMD-SHELL",
          "curl -f http://localhost:${var.container_port}${var.health_path} || exit 1"
        ]
        interval    = 30
        timeout     = 5
        retries     = 3
        startPeriod = 60
      } : null

      environment = var.environment_vars
      secrets     = var.secret_vars

      essential = true

      logConfiguration = var.log_group_name != null ? {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.log_group_name
          awslogs-region        = var.log_region
          awslogs-stream-prefix = "ecs"
        }
      } : null
    }
  ])
}

resource "aws_ecs_service" "ecs_service" {
  name            = var.service
  cluster         = var.cluster_id
  task_definition = aws_ecs_task_definition.task_definition.arn

  desired_count = var.desired_count
  launch_type   = "FARGATE"

  health_check_grace_period_seconds = 60

  network_configuration {
    subnets          = var.private_subnet_ids
    security_groups  = [var.ecs_sg_id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = var.target_group_arn
    container_name   = var.service
    container_port   = var.container_port
  }

  service_connect_configuration {
    enabled   = var.service_connect_enabled
    namespace = var.service_connect_namespace_arn

    dynamic "service" {
      for_each = var.service_connect_discovery_name != null ? [1] : []

      content {
        port_name      = "http"
        discovery_name = var.service_connect_discovery_name

        client_alias {
          dns_name = var.service_connect_discovery_name
          port     = var.container_port
        }
      }
    }
  }

  depends_on = [var.alb_http_listener_arn]
}
