output "target_group_arn" {
  value = aws_lb_target_group.target_group.arn
}

output "http_listener_arn" {
  value = aws_lb_listener.http.arn
}
