# 1단계: 빌드
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# 의존성 먼저 다운로드해서 Docker cache 활용
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


# 2단계: 실행 이미지
# Alpine 기반의 가벼운 JRE 이미지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

ARG BUILD_DATE
ARG GIT_SHA

ENV APP_BUILD_DATE=$BUILD_DATE
ENV APP_GIT_SHA=$GIT_SHA

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]