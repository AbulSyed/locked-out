resource "aws_iam_openid_connect_provider" "github" {
  url = "https://token.actions.githubusercontent.com"

  client_id_list = [
    "sts.amazonaws.com"
  ]
}

data "aws_iam_policy_document" "github_oidc_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRoleWithWebIdentity"]

    principals {
      type        = "Federated"
      identifiers = [aws_iam_openid_connect_provider.github.arn]
    }

    condition {
      test     = "StringEquals"
      values   = ["sts.amazonaws.com"]
      variable = "token.actions.githubusercontent.com:aud"
    }

    condition {
      test     = "StringLike"
      values   = ["repo:AbulSyed/locked-out:ref:refs/heads/main"]
      variable = "token.actions.githubusercontent.com:sub"
    }
  }
}

resource "aws_iam_role" "github_oidc" {
  name               = "github-oidc-role"
  assume_role_policy = data.aws_iam_policy_document.github_oidc_assume_role_policy.json
}

data "aws_iam_policy_document" "build" {
  statement {
    effect = "Allow"

    actions = [
      "ecr:GetAuthorizationToken"
    ]

    resources = ["*"]
  }

  statement {
    effect = "Allow"

    actions = [
      "ecr:BatchCheckLayerAvailability",
      "ecr:CompleteLayerUpload",
      "ecr:UploadLayerPart",
      "ecr:InitiateLayerUpload",
      "ecr:PutImage"
    ]

    resources = [
      module.auth_ecr.repository_arn,
      module.identity_ecr.repository_arn,
      module.frontend_ecr.repository_arn
    ]
  }
}

resource "aws_iam_role_policy" "github_build" {
  name   = "github-build-policy"
  role   = aws_iam_role.github_oidc.id
  policy = data.aws_iam_policy_document.build.json
}
