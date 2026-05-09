resource "aws_secretsmanager_secret" "secret" {
  for_each = var.secrets

  name = each.key
}

resource "aws_secretsmanager_secret_version" "secret_version" {
  for_each = var.secrets

  secret_id      = aws_secretsmanager_secret.secret[each.key].id
  secret_string  = each.value
  version_stages = ["AWSCURRENT"]
}
