# Secrets Manager

data "aws_secretsmanager_secret" "postgres_secret" {
  name = "POSTGRES_PASSWORD"
}

data "aws_secretsmanager_secret_version" "postgres_secret_value" {
  secret_id = data.aws_secretsmanager_secret.postgres_secret.id
}

# Parameter Store

data "aws_ssm_parameter" "postgres_user" {
  name = "/locked-out/postgres_user"
}
