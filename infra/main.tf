module "auth_ecr" {
  source  = "./modules/ecr"
  name    = "auth-service"
  service = "auth"
  env     = "dev"
}

module "identity_ecr" {
  source  = "./modules/ecr"
  name    = "identity-service"
  service = "identity"
  env     = "dev"
}

module "frontend_ecr" {
  source  = "./modules/ecr"
  name    = "frontend-service"
  service = "frontend"
  env     = "dev"
}
