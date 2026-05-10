variable "db_subnet_group_name" {
  description = "Database subnet group name"
  type        = string
}

variable "private_subnet_ids" {
  description = "Private subnet IDs"
  type        = list(string)
}

variable "db_identifier" {
  description = "Identifier for database"
  type        = string
}

variable "engine" {
  description = "Database engine"
  type        = string
}

variable "engine_version" {
  description = "Database engine version"
  type        = string
}

variable "instance_class" {
  description = "Instance type"
  type        = string
}

variable "allocated_storage" {
  description = "Storage"
  type        = number
}

variable "username" {
  description = "Database username"
  type        = string
}

variable "password" {
  description = "Database password"
  type        = string
}

variable "rds_sg_id" {
  description = "RDS Security Group ID"
  type        = string
}
