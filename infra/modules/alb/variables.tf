variable "target_group_name" {
  description = "Target group name"
  type        = string
}

variable "container_port" {
  description = "Container port"
  type        = number
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "health_path" {
  description = "Health check path"
  type        = string
}

variable "listener_arn" {
  description = "Listener ARN"
  type        = string
}

variable "priority" {
  description = "Listener rule priority"
  type        = number
}

variable "target_group_arn" {
  description = "Target group ARN"
  type        = string
}

variable "path" {
  description = "Path"
  type        = string
}
