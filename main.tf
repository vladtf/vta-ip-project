provider "aws" {
  access_key = "${var.AWS_ACCESS_KEY_ID}"
  secret_key = "${var.AWS_SECRET_ACCESS_KEY}"
  region     = "eu-central-1"
}

resource "aws_ecr_repository" "backend_repository" {
  name = "backend_repository"
}

resource "aws_ecr_repository" "frontend_repository" {
  name = "frontend_repository"
}
