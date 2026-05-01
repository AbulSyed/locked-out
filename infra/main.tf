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
  name    = "locked-out-frontend"
  service = "frontend"
  env     = "dev"
}

module "vpc" {
  source                   = "./modules/vpc"
  vpc_name                 = "Locked Out VPC"
  vpc_cidr                 = "10.0.0.0/16"
  public_subnets           = local.public_subnets
  private_subnets          = local.private_subnets
  internet_gateway_name    = "Locked Out Internet Gateway"
  public_rt_destination_ip = "0.0.0.0/0"
  public_rt_name           = "Locked Out Public Route Table"
}

module "sg" {
  source      = "./modules/sg"
  alb_sg_name = "Locked Out Load Balancer SG"
  vpc_id      = module.vpc.vpc_id
}
