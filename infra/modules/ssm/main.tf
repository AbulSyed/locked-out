resource "aws_ssm_parameter" "ssm" {
  for_each = var.ssm_parameters

  name  = each.key
  type  = "String"
  value = each.value
}
