module "ecr" {
  source = "git::https://github.com/AbulSyed/aws-terraform-modules.git//ecr"

  name    = "my-ecr-repo"
  service = "backend"
  env     = "dev"
}
