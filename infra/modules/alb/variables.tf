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

variable "lb_arn" {
  description = "Load Balancer ARN"
  type        = string
}
