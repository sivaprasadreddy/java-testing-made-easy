#!/bin/bash
set -e
set -o pipefail

modules=(
  "junit5-maven-sample"
  "junit5-gradle-sample"
  "junit4-and-5-maven-sample"
  "junit5-assertj-sample"
)

for dir in "${modules[@]}"; do
  echo "================ Building $dir :: BEGIN ====================="
  cd "$dir"
  if [ -f "pom.xml" ]; then
    ./mvnw clean verify
  fi
  if [ -f "build.gradle" ]; then
    ./gradlew clean build
  fi
  echo "================ Building $dir :: END ====================="
  cd ..
done
