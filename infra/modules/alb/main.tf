resource "aws_lb" "alb" {
  name               = var.lb_name
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.security_group_id]
  subnets            = var.public_subnet_ids

  # just for development for terraform destroy
  enable_deletion_protection = false

  tags = {
    name = var.lb_name
  }
}

resource "aws_lb_target_group" "target_group" {
  name        = var.target_group_name
  port        = var.container_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    path                = "/health"
    matcher             = "200"
    healthy_threshold   = 2
    unhealthy_threshold = 2
    interval            = 30
    timeout             = 5
  }
}

resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.alb.arn

  port     = 80
  protocol = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.target_group.arn
  }
}

# TODO configure https listener
# requires SSL cert, can be generated from ACM
# resource "aws_lb_listener" "https" {
#   load_balancer_arn = aws_lb.alb.arn
#
#   port     = 443
#   protocol = "HTTPS"
#
#   ssl_policy      = "ELBSecurityPolicy-2016-08"
#   certificate_arn = var.acm_certificate_arn
#
#   default_action {
#     type             = "forward"
#     target_group_arn = aws_lb_target_group.target_group.arn
#   }
# }
