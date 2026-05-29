# ■ PetCareLog Application

> PetCareLog 프로젝트는 애플리케이션 코드, 인프라 코드, Kubernetes 배포 설정을 분리하여 관리합니다.
> `team5-app`은 Spring Boot 애플리케이션과 CI Workflow를 담당하고, `team5-infra`는 AWS 인프라를 Terraform으로 관리하며, `team5-config`는 Argo CD가 참조하는 GitOps 배포 설정을 관리합니다.
---

## 📑 프로젝트 소개

PetCareLog는 사용자가 반려동물을 등록하고, 반려동물의 케어 기록을 관리할 수 있는 웹 애플리케이션입니다.

---

### 🔔 목표

- 반려동물 돌봄 기록의 간편화
- 날짜별 케어 이력 관리
- 사용자 맞춤형 돌봄 프리셋 제공
- 자주 관리하는 돌봄 항목 추적
- 반려동물별 기록 관리

---

## 💻 기술 스택

### 🚀 Stacks
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA / Hibernate**
- **Thymeleaf**
- **HTML / CSS / JavaScript**
- **MySQL**
- **Redis / Amazon ElastiCache**
- **Amazon S3**

### 🧰 Tools
- **Git**
- **Maven**
- **Docker**
- **Docker Compose**
- **GitHub Actions**
- **Amazon ECR**
- **Kubernetes**
- **ArgoCD**
- **Prometheus**
- **Spring Boot Actuator**
- **Micrometer**

### 👥 Collaboration
- **GitHub**
- **Notion**
- **Discord**

---

## ⚙️ 주요 기능

## 주요 기능

### 👤 회원가입 및 인증
- 이메일, 비밀번호, 닉네임을 입력하여 회원가입할 수 있습니다.
- 회원가입 시 이메일 중복 여부를 확인합니다.
- 가입 완료 후 로그인 페이지로 이동합니다.
- Spring Security의 Form Login을 사용하여 로그인 / 로그아웃을 처리합니다.

### 🔐 로그인 사용자 기반 데이터 관리
- 로그인한 사용자의 이메일을 기준으로 현재 사용자 정보를 조회합니다.
- 반려동물, 돌봄 기록, 프리셋 기능에서 로그인한 사용자 ID를 기준으로 데이터를 처리합니다.
- 사용자별로 자신의 반려동물 목록과 프리셋 설정을 관리할 수 있습니다.

### 🐶 반려동물 관리
- 반려동물을 등록, 조회, 수정, 삭제할 수 있습니다.
- 반려동물 이름, 종, 생년월일 정보를 관리합니다.
- 사용자별 반려동물 목록을 조회할 수 있습니다.
- 반려동물 삭제 시 연결된 이미지도 함께 삭제합니다.

### 🖼️ 반려동물 이미지 관리
- 반려동물 프로필 이미지를 업로드할 수 있습니다.
- Amazon S3를 사용하여 이미지 파일을 저장합니다.
- 기존 이미지가 있는 경우 새 이미지 업로드 시 기존 이미지를 삭제하고 교체합니다.
- 이미지 조회 및 삭제 기능을 제공합니다.
- jpg, jpeg, png, gif, webp 형식만 업로드할 수 있습니다.
- 최대 10MB 이하의 이미지 파일만 허용합니다.

### 📝 돌봄 기록 관리
- 반려동물별 돌봄 기록을 등록, 조회, 수정, 삭제할 수 있습니다.
- 날짜별 돌봄 기록 목록을 조회할 수 있습니다.
- 특정 돌봄 기록의 상세 정보를 조회할 수 있습니다.
- 돌봄 기록의 메모와 기록 시간을 수정할 수 있습니다.
- 월별로 돌봄 기록이 존재하는 날짜 목록을 조회할 수 있습니다.

### ⚡ 빠른 돌봄 기록 등록
- 사용자가 선택한 프리셋을 기반으로 돌봄 기록을 빠르게 등록할 수 있습니다.
- 프리셋의 이름, 카테고리, 아이콘, 색상 정보가 돌봄 기록에 자동 반영됩니다.
- 기록 시간이 입력되지 않으면 현재 시간으로 자동 저장됩니다.

### 🎛️ 돌봄 프리셋 관리
- 기본 프리셋과 사용자 정의 프리셋을 함께 조회할 수 있습니다.
- 카테고리별로 프리셋을 조회할 수 있습니다.
- 사용자 정의 프리셋을 생성, 수정, 삭제할 수 있습니다.
- 프리셋 삭제는 실제 데이터 삭제가 아닌 비활성화 방식으로 처리됩니다.

### ⭐ 추적 프리셋 설정
- 사용자가 자주 기록하거나 확인할 프리셋을 추적 대상으로 설정할 수 있습니다.
- 선택한 프리셋 목록은 사용자별로 저장됩니다.
- 프리셋 조회 시 해당 프리셋이 추적 중인지 여부를 함께 확인할 수 있습니다.

### 🧩 기본 돌봄 프리셋 초기화
- 애플리케이션 실행 시 기본 돌봄 프리셋을 자동으로 생성합니다.
- FOOD, HEALTH, ACTIVITY, GROOMING, POTTY, SYMPTOM 카테고리를 제공합니다.
- 사료, 물, 산책, 목욕, 배변, 구토 등 기본 돌봄 항목을 제공합니다.

### 🧠 Redis 기반 세션 관리
- Spring Session Redis를 사용하여 세션을 Redis에 저장합니다.
- 서버 재시작 또는 다중 서버 환경에서도 세션 유지가 가능하도록 구성했습니다.

### 🕒 공통 생성일 / 수정일 관리
- 공통 BaseTimeEntity를 통해 생성일과 수정일을 자동 관리합니다.
- 데이터 생성 시 `created_at`이 자동 저장됩니다.
- 데이터 수정 시 `updated_at`이 자동 갱신됩니다.

### 📊 헬스 체크 및 모니터링 연동
- Spring Boot Actuator의 `/actuator/health` 엔드포인트를 인증 없이 접근 가능하도록 설정했습니다.
- Kubernetes, 로드밸런서, Prometheus 등 외부 모니터링 환경에서 애플리케이션 상태를 확인할 수 있습니다.

--- 

## 📸 화면

### 🔐 로그인 페이지
- 사용자가 이메일과 비밀번호를 입력하여 로그인할 수 있는 화면
- 로그인 실패 시 오류 메시지 표시
- 회원가입 페이지로 이동 가능

---

### 👤 회원가입 페이지
- 이메일, 비밀번호, 닉네임을 입력하여 회원가입할 수 있는 화면
- 비밀번호는 4자 이상 입력
- 가입 후 로그인 페이지로 이동 가능

---

### 🏠 홈 / 오늘의 돌봄 페이지
- 오늘의 돌봄 기록을 확인하는 메인 화면
- 선택한 반려동물을 기준으로 돌봄 기록 조회
- 주간 / 월간 캘린더를 통해 날짜별 기록 확인 가능
- 프리셋 버튼을 눌러 빠르게 돌봄 기록 등록 가능
- 선택한 날짜의 돌봄 기록 개수와 목록 확인 가능

---

### 📅 캘린더 / 날짜별 기록 화면
- 월별 날짜를 확인할 수 있는 캘린더 제공
- 기록이 있는 날짜를 표시
- 특정 날짜를 선택하면 해당 날짜의 돌봄 기록 목록 확인 가능

---

### 📝 돌봄 기록 상세 모달
- 선택한 돌봄 기록의 상세 내용을 확인할 수 있는 화면
- 메모와 기록 시간을 수정 가능
- 돌봄 기록 삭제 가능

---

### 📊 주간 트래커 페이지
- 선택한 프리셋을 기준으로 주간 루틴을 확인하는 화면
- 일주일 단위로 돌봄 수행 여부를 시각적으로 확인 가능
- 반려동물별 루틴 관리에 활용 가능

---

### ⚙️ 관리 페이지
- 반려동물과 프리셋을 관리하는 화면
- 로그아웃 가능
- 현재 선택된 반려동물 프로필 확인 가능
- 반려동물 추가, 프리셋 관리, 주간 트래커 요소 관리 화면으로 이동 가능
- 등록된 반려동물 목록 확인 가능

---

### 🐶 반려동물 등록 / 수정 페이지
- 반려동물 정보를 등록하거나 수정할 수 있는 화면
- 프로필 사진 업로드 가능
- 이름, 종류, 생일 입력 가능
- 생일 모름 체크 가능
- 반려동물 삭제 가능

---

### 🖼️ 반려동물 이미지 업로드 화면
- 반려동물 프로필 사진을 선택하고 미리보기 가능
- 이미지 선택 시 화면에 즉시 미리보기 표시
- 반려동물 저장 시 프로필 이미지로 활용 가능

---

### 🧩 할 일 프리셋 관리 페이지
- 돌봄 기록에 사용할 프리셋 목록을 관리하는 화면
- 프리셋을 선택하여 수정 가능
- 새로운 프리셋 추가 화면으로 이동 가능
- 필요 없는 프리셋 삭제 가능

---

### ➕ 프리셋 등록 / 수정 페이지
- 사용자 맞춤 돌봄 프리셋을 생성하거나 수정할 수 있는 화면
- 프리셋 제목 입력 가능
- 카테고리 선택 가능
- 아이콘 선택 가능
- 프리셋 저장, 취소, 삭제 가능

---

### ✅ 주간 트래커 요소 관리 페이지
- 주간 트래커에 표시할 돌봄 항목을 선택하는 화면
- 자주 확인할 프리셋을 선택 가능
- 선택한 항목 저장 가능

---

## 로컬 실행 방법

로컬 실행을 위해 다음이 필요합니다.

* Java 17
* Maven
* MySQL
* Redis
* AWS S3 사용 시 AWS 자격 증명 또는 로컬 테스트용 설정

### 환경 변수

애플리케이션은 다음 환경 변수를 사용합니다.

| 환경 변수               | 설명                 |
| ------------------- | ------------------ |
| DB_HOST             | MySQL 호스트          |
| DB_PORT             | MySQL 포트           |
| DB_NAME             | 데이터베이스 이름          |
| DB_USERNAME         | 데이터베이스 사용자명        |
| DB_PASSWORD         | 데이터베이스 비밀번호        |
| REDIS_HOST          | Redis 호스트          |
| REDIS_PORT          | Redis 포트           |
| REDIS_USERNAME      | Redis 사용자명         |
| REDIS_PASSWORD      | Redis 비밀번호         |
| REDIS_SSL_ENABLED   | Redis SSL 사용 여부    |
| AWS_REGION          | AWS 리전             |
| S3_BUCKET_NAME      | 이미지 저장용 S3 버킷 이름   |
| S3_PET_IMAGE_PREFIX | 반려동물 이미지 저장 prefix |

### application.yaml 주요 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT:3306}/${DB_NAME}?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  session:
    store-type: redis
    timeout: 30m
    redis:
      namespace: petcarelog:session

  data:
    redis:
      host: ${REDIS_HOST}
      port: ${REDIS_PORT:6379}
      username: ${REDIS_USERNAME:}
      password: ${REDIS_PASSWORD:}
      ssl:
        enabled: ${REDIS_SSL_ENABLED:false}

aws:
  region: ${AWS_REGION}
  s3:
    bucket: ${S3_BUCKET_NAME}
    pet-image-prefix: ${S3_PET_IMAGE_PREFIX}

management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```

### Maven으로 실행

```bash
./mvnw spring-boot:run
```

Windows 환경에서는 다음 명령어를 사용할 수 있습니다.

```bash
mvnw.cmd spring-boot:run
```

Maven이 로컬에 설치되어 있다면 다음 명령어도 사용할 수 있습니다.

```bash
mvn spring-boot:run
```

### JAR 빌드 및 실행

```bash
./mvnw clean package
```

Windows 환경:

```bash
mvnw.cmd clean package
```

빌드된 JAR 실행:

```bash
java -jar target/*.jar
```

테스트를 제외하고 빌드하려면 다음 명령어를 사용할 수 있습니다.

```bash
./mvnw clean package -DskipTests
```

## Docker 이미지 빌드

```bash
docker build -t petcarelog:local .
```

실행 예시:

```bash
docker run -p 8080:8080 \
  -e DB_HOST=localhost \
  -e DB_PORT=3306 \
  -e DB_NAME=petcarelog_dev \
  -e DB_USERNAME=petcare \
  -e DB_PASSWORD=petcare1234 \
  -e REDIS_HOST=localhost \
  -e REDIS_PORT=6379 \
  -e REDIS_SSL_ENABLED=false \
  petcarelog:local
```

---

## CI/CD 구조

`team5-app` 레포는 GitHub Actions를 사용하여 애플리케이션을 Docker 이미지로 빌드하고 Amazon ECR에 push합니다.

개발 환경은 `dev` 브랜치에 push되면 dev 배포 workflow가 실행되고,  
운영 환경은 `main` 브랜치에 push되면 prod 배포 workflow가 실행됩니다.

빌드된 이미지는 각 환경별 ECR Repository에 `github.sha` 태그와 `latest` 태그로 push됩니다.

이미지 push가 완료되면 GitHub Actions가 `team5-config` 레포를 checkout한 뒤,  
각 환경의 Kubernetes Deployment patch 파일에 있는 image 값을 새 이미지 태그로 수정합니다.

이후 변경된 manifest를 `team5-config` 레포의 `main` 브랜치에 commit/push하면,  
Argo CD가 변경 사항을 감지하여 각 환경의 EKS 클러스터에 자동 배포합니다.

---

### dev / prod 배포 흐름

```text
team5-app dev 브랜치 push
→ GitHub Actions 실행
→ AWS OIDC 인증으로 dev IAM Role Assume
→ Amazon ECR 로그인
→ Docker image build
→ dev ECR에 이미지 push
→ team5-config 레포 checkout
→ dev Deployment image tag 수정
→ team5-config main 브랜치에 commit/push
→ Argo CD가 변경 감지
→ dev EKS에 자동 배포

team5-app main 브랜치 push
→ GitHub Actions 실행
→ AWS OIDC 인증으로 prod IAM Role Assume
→ Amazon ECR 로그인
→ Docker image build
→ prod ECR에 이미지 push
→ team5-config 레포 checkout
→ prod Deployment image tag 수정
→ team5-config main 브랜치에 commit/push
→ Argo CD가 변경 감지
→ prod EKS에 자동 배포

---

## GitHub Actions 환경 변수 및 Secret

GitHub Actions에서 사용하는 주요 값은 GitHub Repository Variables와 Secrets로 관리합니다.

### Repository Variables

| 이름                  | 설명                     |
| ------------------- | ---------------------- |
| AWS_REGION          | AWS 리전                 |
| ECR_DEV_REPOSITORY  | dev ECR Repository 이름  |
| ECR_PROD_REPOSITORY | prod ECR Repository 이름 |

### Repository Secrets

| 이름                | 설명                                               |
| ----------------- | ------------------------------------------------ |
| AWS_ROLE_DEV_ARN  | dev ECR push 권한을 가진 GitHub Actions IAM Role ARN  |
| AWS_ROLE_PROD_ARN | prod ECR push 권한을 가진 GitHub Actions IAM Role ARN |
| CONFIG_REPO_TOKEN | team5-config 레포의 image tag를 수정하기 위한 GitHub token |
