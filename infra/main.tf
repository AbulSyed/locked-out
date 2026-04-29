module "auth_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-auth-service"
  service = "auth"
  env     = "dev"
}

module "identity_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-identity-service"
  service = "identity"
  env     = "dev"
}

module "frontend_ecr" {
  source  = "./modules/ecr"
  name    = "locked-out-frontend-service"
  service = "frontend"
  env     = "dev"
}

module "vpc" {
  source   = "./modules/vpc"
  name     = "vpc"
  ip_range = "10.0.0.0/24"
}
