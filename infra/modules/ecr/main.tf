resource "aws_ecr_repository" "locked_out_ecr" {
  name = var.name

  image_scanning_configuration {
    scan_on_push = true
  }

  force_delete = true

  tags = {
    Service     = var.service
    Environment = var.env
  }
}
