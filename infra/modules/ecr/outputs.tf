output "repository_url" {
  value = aws_ecr_repository.locked_out_ecr.repository_url
}

output "repository_arn" {
  value = aws_ecr_repository.locked_out_ecr.arn
}
