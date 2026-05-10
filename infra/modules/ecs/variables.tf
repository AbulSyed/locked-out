variable "task_def_ecs_family" {
  description = "Task Definition family"
  type        = string
}

variable "task_def_cpu" {
  description = "Task Definition CPU"
  type        = string
}

variable "task_def_memory" {
  description = "Task Definition Memory"
  type        = string
}

variable "task_execution_role_arn" {
  description = "Task Execution Role ARN"
  type        = string
}

variable "service" {
  description = "Name of service"
  type        = string
}

variable "ecr_url" {
  description = "ECR URL"
  type        = string
}

variable "container_port" {
  description = "Container port"
  type        = number
}

variable "environment_vars" {
  description = "Environment variables (non secret)"
  type = list(object({
    name  = string
    value = string
  }))
}

variable "secret_vars" {
  description = "Secret environment variables"
  type = list(object({
    name  = string
    value = string
  }))
}

variable "log_group_name" {
  description = "Log group name"
  type        = string
}

variable "log_region" {
  description = "AWS region"
  type        = string
}

variable "cluster_id" {
  description = "Cluster ID"
  type        = string
}

variable "desired_count" {
  description = "Desired number of running instances"
  type        = number
}

variable "private_subnet_ids" {
  description = "List of private subnet IDs"
  type        = list(string)
}

variable "ecs_sg_id" {
  description = "ECS Security Group ID"
  type        = string
}

variable "target_group_arn" {
  description = "Target group ARN"
  type        = string
}

variable "alb_http_listener_arn" {
  description = "ALB HTTP listener ARN"
  type        = string
}
