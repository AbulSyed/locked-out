variable "vpc_name" {
  description = "VPC name"
  type        = string
}

variable "vpc_cidr" {
  description = "VPC CIDR"
  type        = string
}

variable "public_subnets" {
  description = "Public subnet config"
  type = map(object({
    cidr = string
    az   = string
  }))
}

variable "private_subnets" {
  description = "Private subnet config"
  type = map(object({
    cidr = string
    az   = string
  }))
}

variable "internet_gateway_name" {
  description = "Internet Gateway name"
  type        = string
}

variable "public_rt_destination_ip" {
  description = "Public route table destination IP"
  type        = string
}

variable "public_rt_name" {
  description = "Public route table name"
  type        = string
}

variable "nat_eip_name" {
  description = "NAT EIP name"
  type        = string
}

variable "nat_gateway_name" {
  description = "NAT EIP name"
  type        = string
}

variable "private_rt_destination_ip" {
  description = "Private route table destination IP"
  type        = string
}

variable "private_rt_name" {
  description = "Private route table name"
  type        = string
}
