# 파일 이동 스케줄러 (A → B)

## 📖 개요

Spring Boot 스케줄러로 지정된 파일 1개를 소스(A)에서 목적지(B)로 주기적으로 이동하는 연습용 프로젝트입니다.

---

## 🛠 기술 스택
- Java 17
- Spring Boot 3.4
- Spring Scheduling
- Gradle


## 🔍 주요 기능
- mvExport.target.filename에 지정한 단일 파일 이동
- 파일의 Size 측정
- mvExport.enabled로 전체 on/off 스위치
- 매 분 0초에 스케줄 실행(기본 설정)


## 🧪 학습 포인트
- 단순 파일 I/O와 예외 대비(이동 실패 시 폴백)
- Spring Scheduling의 cron 표현식 사용
- 환경 변수/프로퍼티 기반 동작 제어
