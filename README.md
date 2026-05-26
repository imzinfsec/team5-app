# ■ PetCareLog

> 반려동물의 일상 돌봄 기록을 쉽고 빠르게 관리하는 케어 기록 플랫폼

---

## 📑 프로젝트 소개
PetCareLog는 사용자가 **반려동물의 돌봄 활동을 기록하고 관리할 수 있는 서비스**입니다.  
사료 급여, 산책, 목욕, 약 복용 등 반복되는 돌봄 항목을 프리셋으로 관리하고, 날짜별로 돌봄 기록을 확인할 수 있도록 돕는 것을 목표로 합니다.

현재 프로젝트는 반려동물 관리, 돌봄 프리셋 관리, 돌봄 기록 관리 기능을 중심으로 구성되어 있습니다.  
반려동물 정보는 `pets`, 돌봄 프리셋은 `care_presets`, 사용자별 추적 설정은 `user_preset_settings`, 돌봄 기록은 `care_logs` 도메인으로 분리되어 있습니다.

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
- **JPA (Hibernate)**
- **Thymeleaf**
- **HTML / CSS / JavaScript**
- **MySQL**

### 🧰 Tools
- **Git**
- **Docker**
- **Docker Compose**

### 👥 Collaboration
- **GitHub**
- **Notion**
- **Discord**

---

## ⚙️ 주요 기능

### 🐶 반려동물 관리
- 반려동물 등록
- 사용자별 반려동물 목록 조회
- 반려동물 단건 조회
- 반려동물 정보 수정
- 반려동물 삭제

### 🧩 돌봄 프리셋 관리
- 사용자 맞춤 돌봄 프리셋 생성
- 기본 프리셋 및 사용자 프리셋 목록 조회
- 카테고리별 프리셋 조회
- 프리셋 단건 조회
- 프리셋 정보 수정
- 프리셋 비활성화 삭제

### ✅ 추적 항목 관리
- 사용자가 자주 관리할 돌봄 프리셋 선택
- 선택한 프리셋을 추적 항목으로 저장
- 사용자별 추적 프리셋 상태 관리

### 📝 돌봄 기록 관리
- 프리셋 기반 빠른 돌봄 기록 생성
- 특정 날짜의 돌봄 기록 조회
- 돌봄 기록 단건 조회
- 돌봄 기록 수정
- 돌봄 기록 삭제
- 월별 기록이 존재하는 날짜 조회

### 🩺 서버 상태 확인
- `/api/health`를 통한 서버 상태 확인

--- 

## 📸 화면

### 🏠 메인 페이지
- 서비스의 첫 진입 화면
- 반려동물 돌봄 기록 서비스 소개
- 반려동물 등록 또는 돌봄 기록 관리 화면으로 이동 가능
<img width="390" height="949" alt="image" src="https://github.com/user-attachments/assets/8277c069-9259-4e8f-b603-0c069de2a480" />

---

### 🐶 반려동물 목록 페이지
- 사용자가 등록한 반려동물 목록을 확인할 수 있는 화면
- 반려동물 이름, 종, 생년월일 정보 확인 가능
<img width="387" height="952" alt="image" src="https://github.com/user-attachments/assets/e237a7e2-37ac-49a7-89f8-b2fb9a701dca" />

---

### 🐶 반려동물 등록 페이지
- 새로운 반려동물을 등록할 수 있는 화면
- 반려동물 이름, 종, 생년월일 등 기본 정보 입력 가능
<img width="385" height="952" alt="image" src="https://github.com/user-attachments/assets/ea3cc650-1cdc-4df3-8dc6-3892e1ac28d8" />

---

### 🧩 돌봄 프리셋 목록 페이지
- 기본 돌봄 프리셋과 사용자 맞춤 프리셋을 확인할 수 있는 화면
- 사료, 산책, 목욕, 약 복용 등 돌봄 항목 확인 가능
- 카테고리별 프리셋 조회 가능
<img width="383" height="950" alt="image" src="https://github.com/user-attachments/assets/b5fdd6da-f758-4430-b823-a711a8b7d61d" />

---

### 🧩 돌봄 프리셋 등록 페이지
- 사용자 맞춤 돌봄 프리셋을 생성할 수 있는 화면
- 프리셋 이름, 카테고리, 아이콘, 색상, 정렬 순서 입력 가능
<img width="385" height="948" alt="image" src="https://github.com/user-attachments/assets/a2101dcf-1536-4dd2-a580-acd81a38a01c" />
<img width="387" height="946" alt="image" src="https://github.com/user-attachments/assets/11f29cc6-220b-440c-8d8d-2ec770916769" />

---

### 📝 돌봄 기록 작성 페이지
- 반려동물의 돌봄 활동을 기록할 수 있는 화면
- 기록 시간과 메모 입력 가능
<img width="385" height="948" alt="image" src="https://github.com/user-attachments/assets/e754f812-8427-4018-886f-4ebf715d0cb0" />

---

### 📅 날짜별 돌봄 기록 페이지
- 특정 날짜의 돌봄 기록을 확인할 수 있는 화면
- 사료 급여, 산책, 목욕 등 날짜별 케어 이력 확인 가능
- 돌봄 기록 상세 페이지로 이동 가능
<img width="387" height="947" alt="image" src="https://github.com/user-attachments/assets/88b18e81-b357-4933-8620-aff423f13dfb" />

---

### 📆 월별 기록 캘린더 페이지
- 월별로 돌봄 기록이 존재하는 날짜를 확인할 수 있는 화면
- 기록이 있는 날짜를 선택하여 해당 날짜의 돌봄 기록 조회 가능
<img width="385" height="951" alt="image" src="https://github.com/user-attachments/assets/771483d3-80e8-42ac-935f-9f41d7eafa70" />
