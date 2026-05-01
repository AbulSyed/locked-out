resource "aws_vpc" "vpc" {
  cidr_block = var.vpc_cidr

  tags = {
    Name = var.vpc_name
  }
}

resource "aws_subnet" "public_subnets" {
  for_each = var.public_subnets

  vpc_id            = aws_vpc.vpc.id
  cidr_block        = each.value.cidr
  availability_zone = each.value.az

  tags = {
    Name = "Locked Out Public ${each.key}"
  }
}

resource "aws_subnet" "private_subnets" {
  for_each = var.private_subnets

  vpc_id            = aws_vpc.vpc.id
  cidr_block        = each.value.cidr
  availability_zone = each.value.az

  tags = {
    Name = "Locked Out Private ${each.key}"
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name = var.internet_gateway_name
  }
}

resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = var.public_rt_destination_ip
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = var.public_rt_name
  }
}

resource "aws_route_table_association" "public_rt_subnet_association" {
  for_each       = var.public_subnets
  subnet_id      = aws_subnet.public_subnets[each.key].id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_eip" "nat_eip" {
  for_each = var.public_subnets

  domain = "vpc"

  tags = {
    Name = "${var.nat_eip_name} ${each.key}"
  }
}

resource "aws_nat_gateway" "ngw" {
  for_each = var.public_subnets

  allocation_id = aws_eip.nat_eip[each.key].id

  # public subnet, as NAT needs internet access via IGW
  subnet_id = aws_subnet.public_subnets[each.key].id

  depends_on = [aws_internet_gateway.gw]

  tags = {
    Name = "${var.nat_gateway_name} ${each.key}"
  }
}

resource "aws_route_table" "private_rt" {
  for_each = var.private_subnets

  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block     = var.private_rt_destination_ip
    nat_gateway_id = aws_nat_gateway.ngw[each.key].id
  }

  tags = {
    Name = "${var.private_rt_name} ${each.key}"
  }
}

resource "aws_route_table_association" "private_rt_subnet_association" {
  for_each = var.private_subnets

  subnet_id      = aws_subnet.private_subnets[each.key].id
  route_table_id = aws_route_table.private_rt[each.key].id
}
