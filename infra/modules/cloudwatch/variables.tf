variable "cluster_name" {
  description = "Name of Cluster"
  type        = string
}

variable "service" {
  description = "Service name"
  type        = string
}

variable "log_retention_days" {
  description = "Log retention days"
  type        = number
}

variable "env" {
  description = "Env"
  type        = string
}
