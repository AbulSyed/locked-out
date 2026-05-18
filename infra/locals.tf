locals {
  public_subnets = {
    az1 = {
      cidr = "10.0.0.0/24"
      az   = "eu-west-2a"
    }
    az2 = {
      cidr = "10.0.1.0/24"
      az   = "eu-west-2b"
    }
  }

  private_subnets = {
    az1 = {
      cidr = "10.0.2.0/24"
      az   = "eu-west-2a"
    }
    az2 = {
      cidr = "10.0.3.0/24"
      az   = "eu-west-2b"
    }
  }
}
