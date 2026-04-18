terraform {
  backend "s3" {
    bucket       = "locked-out-terraform-state-588738611864"
    key          = "terraform/terraform.tfstate"
    region       = "eu-west-2"
    use_lockfile = true
  }
}
