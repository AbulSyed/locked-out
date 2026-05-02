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

variable "ecr_url" {
  description = "ECR URL"
  type        = string
}

variable "container_port" {
  description = "Container port"
  type        = number
}
