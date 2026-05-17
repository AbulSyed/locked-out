# alb resource in main.tf

resource "aws_lb_target_group" "target_group" {
  name        = var.target_group_name
  port        = var.container_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    path                = var.health_path
    matcher             = "200"
    healthy_threshold   = 2
    unhealthy_threshold = 3
    interval            = 30
    timeout             = 10
  }
}

# default listener returns 404 in main.tf

resource "aws_lb_listener_rule" "service_rule" {
  listener_arn = var.listener_arn
  priority     = var.priority

  action {
    type             = "forward"
    target_group_arn = var.target_group_arn
  }

  condition {
    path_pattern {
      values = [var.path]
    }
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
