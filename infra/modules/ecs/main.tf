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
      name  = "app"
      image = "${var.ecr_url}:latest"

      portMappings = [
        {
          containerPort = var.container_port
          protocol      = "tcp"
        }
      ]

      essential = true
    }
  ])
}
