Continuous Integration (CI)
---
The GitHub action `ci.yml` contains the following:
1. run `./gradlew check` to make sure all tests pass.
2. use [semantic-release](https://github.com/semantic-release/semantic-release) to manage release versions.
    - `CHANGELOG` will be updated if a new release being generated.
    - `version` in `gradle.properties` will be updated with the new version number.
    - new version `tag` will be created.
3. dockerise the application
    - `./gradlew assemble` to package the artifact (uber-jar).
    - use `docker build` to build the application and image `tag` with SHA and version.
      - `Dockerfile` will package the uber-jar from `./gradle assemble` to the image.
    - use `docker push` to push the image to Amazon ECR.
      - the image will have two tags, one is the application version, the other one is the SHA of the commit as tag.

Setup
---
1. This project uses [pre-commit](https://pre-commit.com) with the following hooks. 
   ```shell
   pre-commit install --install-hooks -t pre-commit -t commit-msg -t pre-push
   ```
2. If installed correctly, it will:
   1. check whether the commit message is following [conventional-commit](https://www.conventionalcommits.org/en/v1.0.0/) format.
   2. run `./gradlew build` before push to make sure all tests passes.
3. Please update `settings.gradle.kts` and `gradle.properties` to the correct name and version (0.0.1) of the project.
4. remove the `CHANGELOG.md` file

Amazon ECR Credentials
---
As this project uses [Amazon ECR](https://aws.amazon.com/ecr/) as the application image repository, 
it will require all the necessary credentials being setup in AWS.

1. Create a dedicated system user in AWS which contains these permissions.
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "ecr:PutLifecyclePolicy",
                "ecr:CreateRepository",
                "ecr:DescribeRepositories"
            ],
            "Resource": "arn:aws:ecr:*:<your-account-id>:repository/*"
        },
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": "ecr:DescribeRegistry",
            "Resource": "*"
        }
    ]
}
```
2. Generate API Key and Secret for this user.
3. Import the API Key as `AWS_ACCESS_KEY_ID` and API Secret as `AWS_SECRET_ACCESS_KEY` into the GitHub Project Settings.
   - Settings -> Secrets -> Actions and create the two secrets. (ex: https://github.com/lawkai/http4k-template/settings/secrets/actions)
