terraform {
  backend "s3" {
    bucket = "terraform-state-bucket"
    key    = "terraform.tfstate"
    region = "eu-central-1"
  }
}

provider "aws" {
  region = "eu-central-1"
}

resource "aws_ecr_repository" "backend_repository" {
  name = "backend_repository"
}

resource "aws_ecr_repository" "frontend_repository" {
  name = "frontend_repository"
}
