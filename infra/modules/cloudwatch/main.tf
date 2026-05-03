resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name              = "/ecs/${var.cluster_name}/${var.service}"
  retention_in_days = var.log_retention_days

  tags = {
    Environment = var.env
    Service     = var.service
  }
}
