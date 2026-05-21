# studio-udtt-backend

> 프로젝트 우당탕탕 공식 웹사이트의 백엔드 서버

프로젝트 의뢰, 참여 신청, 관리자 기능, 문자 발송 등 서비스 운영에 필요한 주요 기능을 처리하는 백엔드 서버입니다.

---

## 기술 스택

|    영역     |             기술             |
|------------|-----------------------------|
|  Language  |  Java 25                    |
|  Framework | Spring Boot 3.5             |
|  Database  | PostgreSQL                  |
|  ORM       | Spring Data JPA             |
|  Auth      | Spring Security + JWT       |
|  SMS       | CoolSMS SDK                 |
|  Crawling  | Jsoup                       |
|  Docs      | Swagger (springdoc-openapi) |
|  Infra     | Docker                      |

---

## 프로젝트 구조

```text
src/
└── main/
    ├── java/com/udtt/backend/
    │   ├── admin/              # 관리자 기능
    │   ├── content/            # 콘텐츠 관리
    │   ├── global/             # 공통 설정, 예외 처리, 유틸
    │   ├── project/            # 프로젝트 의뢰 및 참여 관리
    │   ├── sms/                # 문자 발송 관리
    │   ├── stat/               # 누적 데이터 및 통계 관리
    │   └── survey/             # 설문/폼 데이터 관리
    │
    │   각 기능 패키지는 필요에 따라 아래와 같은 계층으로 구성됩니다.
    │   ├── controller/
    │   ├── dto/
    │   ├── entity/
    │   ├── enums/
    │   ├── repository/
    │   └── service/
    │
    └── resources/
        ├── application.properties
        └── application-prod.properties
