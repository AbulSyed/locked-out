resource "aws_ssm_parameter" "hardcoded_ssm_values" {
  for_each = var.ssm_parameters

  name  = each.key
  type  = "String"
  value = each.value
}
