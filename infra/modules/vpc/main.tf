resource "aws_vpc" "this" {
  cidr_block = var.ip_range

  tags = {
    Name = var.name
  }
}
