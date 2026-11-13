# grails-upload-file

Sample app for the apache/grails-static-website guide [How to upload a file with Grails](https://grails.apache.org/guides/grails-upload-file/8/guide/index.html).

This branch is the Grails 8 companion for **uploading files**: a `MultipartFile` command object with validation, raising the upload size limit with `spring.servlet.multipart`, and three ways to store a featured image:

| Domain class | Where the image goes |
|---|---|
| `Restaurant` | In the database, as a `byte[]` column |
| `PointOfInterest` | In a local folder, served by the app under `/uploads/**` |
| `Hotel` | In an AWS S3 bucket, with the AWS SDK for Java 2.x |

## Layout

| Directory | What it is |
|---|---|
| [`initial/`](initial/) | Grails 8 web app (`web` profile, Hibernate, Tomcat, H2) with CRUD pages for restaurants, points of interest, and hotels, and no upload code. Start here and follow the guide. |
| [`complete/`](complete/) | The finished app: domain classes, data services, upload services, controllers, views, and tests. |

## Running

Requires JDK 21+.

```bash
cd complete
./gradlew test integrationTest
./gradlew bootRun
```

Then browse to http://localhost:8080

The S3 integration test runs against [LocalStack](https://www.localstack.cloud/) with Testcontainers, so it needs Docker. Without Docker it is skipped.

To upload hotel images to a real bucket, set the bucket and region, and provide AWS credentials through the [default credentials provider chain](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials-chain.html):

```bash
AWS_S3_BUCKET=my-bucket AWS_S3_REGION=eu-west-1 \
AWS_ACCESS_KEY_ID=... AWS_SECRET_ACCESS_KEY=... \
./gradlew bootRun
```

## Branches

| Branch | Grails version |
|---|---|
| `grails8` | Apache Grails 8.0.0 |
| `grails5` | Apache Grails 5 (published guide baseline) |

## Guide prose

Published narrative lives on [grails.apache.org/guides](https://grails.apache.org/guides/) in [apache/grails-static-website](https://github.com/apache/grails-static-website) under `guides/grails-upload-file/`.

## License

Apache License 2.0. See [LICENSE](LICENSE).
