# studio-udtt-backend

> 프로젝트 우당탕탕 공식 웹사이트의 백엔드 서버

프로젝트 의뢰/참여 신청 관리, 관리자 기능, 문자 발송 등을 담당하는 REST API 서버입니다.

---

## 🛠 기술 스택

|    분류     |             기술             |
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

## 📁 프로젝트 구조

src/
└── main/
    ├── java/com/udtt/backend/
    │   ├── admin/     # 관리자 기능, admin만 추가로 contents를 하위 폴더로 하나 가지고 있음
    │   │   ├── controller/    
    │   │   ├── dto/           
    │   │   ├── entity/         
    │   │   ├── enums/         # SUPER_ADMIN(프로그램 관리자), ADMIN(사용자 - 대표님)
    │   │   ├── repository/     
    │   │   └── service/         
    │   │
    │   ├── content/   # 콘텐츠 관리
    │   ├── global/    # 공통 설정, 예외처리, 유틸
    │   ├── project/   # 프로젝트 총괄 관리 (의뢰 신청, 의뢰 참여 관리 등)
    │   ├── sms/       # 문자 전송을 위한 entity, controller 등을 포함
    │   ├── stat/      # 누적 데이터 관리 및 통계 관리
    │   └── survey/    # 우당탕탕 회사 자체의 구글폼 처리 (미완)
    └── resources/
        ├── application.properties
        └── application-prod.properties
