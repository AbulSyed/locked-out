data "aws_secretsmanager_secret" "postgres_secret" {
  name = "DB_PASSWORD"
}

data "aws_secretsmanager_secret_version" "postgres_secret_value" {
  secret_id = data.aws_secretsmanager_secret.postgres_secret.id
}
