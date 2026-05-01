variable "alb_sg_name" {
  description = "Load Balancer Security Group name"
  type        = string
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "ecs_sg_name" {
  description = "ECS Security Group name"
  type        = string
}

variable "auth_service_port" {
  description = "Auth Service Port"
  type        = number
}

variable "identity_service_port" {
  description = "Identity Service Port"
  type        = number
}

variable "frontend_port" {
  description = "Frontend Port"
  type        = number
}
