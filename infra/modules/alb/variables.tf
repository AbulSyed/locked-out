variable "security_group_id" {
  description = "ALB Security group ID"
  type        = string
}

variable "public_subnet_ids" {
  description = "Security group IDs"
  type        = list(string)
}

variable "lb_name" {
  description = "Load balancer name"
  type        = string
}

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
