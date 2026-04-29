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
  source                   = "./modules/vpc"
  vpc_name                 = "Locked Out VPC"
  vpc_cidr                 = "10.0.0.0/24" # 2^8 = 256 IPs (note 5 are reserved by AWS)
  public_subnet_name       = "Locked Out Public Subnet"
  public_subnet_cidr       = "10.0.0.0/25" # 10.0.0.0 → 10.0.0.127
  private_subnet_name      = "Locked Out Private Subnet"
  private_subnet_cidr      = "10.0.0.128/25" # 10.0.0.128 → 10.0.0.255
  subnet_availability_zone = "eu-west-2a"
}
