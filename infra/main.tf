module "ecr" {
  source = "git::https://github.com/AbulSyed/aws-terraform-modules.git//ecr"

  name    = "my-ecr-repo"
  service = "auth"
  env     = "dev"
}

module "vpc" {
  source = "git::https://github.com/AbulSyed/aws-terraform-modules.git//vpc"

  vpc_name                  = "My VPC"
  vpc_cidr                  = "10.0.0.0/16"
  public_subnets            = local.public_subnets
  private_subnets           = local.private_subnets
  internet_gateway_name     = "My Internet Gateway"
  public_rt_destination_ip  = "0.0.0.0/0"
  public_rt_name            = "My Public Route Table"
  nat_eip_name              = "My NAT EIP"
  nat_gateway_name          = "My NAT GW"
  private_rt_destination_ip = "0.0.0.0/0"
  private_rt_name           = "My Private Route Table"
}

module "vpc" {
  source = "git::https://github.com/AbulSyed/aws-terraform-modules.git//s3"

  bucket = "state-bucket-123"
  bucket_name = "Terraform State Bucket"
}
