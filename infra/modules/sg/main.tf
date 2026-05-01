resource "aws_security_group" "alb_sg" {
  name   = var.alb_sg_name
  vpc_id = var.vpc_id

  tags = {
    Name = var.alb_sg_name
  }
}

resource "aws_security_group_rule" "alb_http" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group_rule" "alb_https" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group_rule" "alb_egress" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group" "ecs_sg" {
  name   = var.ecs_sg_name
  vpc_id = var.vpc_id

  tags = {
    Name = var.ecs_sg_name
  }
}

resource "aws_security_group_rule" "ecs_ingress_from_alb_auth" {
  type                     = "ingress"
  from_port                = var.auth_service_port
  to_port                  = var.auth_service_port
  protocol                 = "tcp"
  security_group_id        = aws_security_group.ecs_sg.id
  source_security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group_rule" "ecs_ingress_from_alb_identity" {
  type                     = "ingress"
  from_port                = var.identity_service_port
  to_port                  = var.identity_service_port
  protocol                 = "tcp"
  security_group_id        = aws_security_group.ecs_sg.id
  source_security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group_rule" "ecs_ingress_from_alb_frontend" {
  type                     = "ingress"
  from_port                = var.frontend_port
  to_port                  = var.frontend_port
  protocol                 = "tcp"
  security_group_id        = aws_security_group.ecs_sg.id
  source_security_group_id = aws_security_group.alb_sg.id
}

resource "aws_security_group_rule" "ecs_egress" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ecs_sg.id
}
