
## Batch_PT_service 프로젝트 설명
실제 프로젝트가 아닌, 공부용 프로젝트이다. (past campus)
실무에서 로깅/추척, 트랜잭션 관리, 통계, 리소스 관리 등의 어려움을 느끼고 이러한 문제를 해결하기 위해 
Spring Batch를  사용하여 PT 서비스를 직접 구현하면서 공부하고 있다.

## Batch 프로젝트 구성
이용권 관리 서비스 화면은 구현은 무료 테마를 사용할 것이다.

Spring Boot, Gradle, Spring Batch, MariaDB, JPA,  Lombok, Model Mapper, Docker Desktop, DBeaver 또는 HeidiSQL, IntelliJ IDEA

## 이용권 서비스 요구사항

1. 이용권
   + 사용자는 N개의 이용권을 가질 수 있다.
   + 이용권은 횟수가 모두 소진되거나 이용기간이 지나면 만료된다.
   + 이용권 만료 전 사용자에게 알림을 준다.
   + 업체에서 원하는 시간을 설정하여 일괄로 사용자에게 이용권을 지급할 수 있다.
  
2. 수업
   + 예약된 수업 10분 전 출석 안내 알람을 준다.
   + 수업 종료 시간 시점 수업을 예약한 학생의 이용권 횟수를 일괄로 차감한다. (-1)

3. 통계
   + 사용자의 수업 예약, 출석, 이용권 횟수 등의 데이터로 유의미한 통계 데이터를 만든다.


## 테이블 설계
N개의 체육관을 기준으로 하지 않고 1개의 체육관을 기준으로 프로젝트를 구현했다.(체육관에 관련된 id, 데이터 삭제)
또한, 유의미한 통계 데이터 테이블을 자동으로 생성한다.

## 배치 구조 설계
1. 이용권 만료 (ItemReader, ItemWriter)

2. 이용권 일괄 지급(Tasklet)

3. 예약 수업 전 알람(Multi-Threaded-Step)
카카오 메세지 API를 사용하여 구현

4. 수업 종료 후 이용권 차감(AsynclItemProcessor, AsynclItemWriter)

5. 통계 데이터 구현(Parallel Steps)

## 테스트 케이스 작성
JUnit,Mockito를 사용한 테스트 작성


추후 진행 예정?
1.배포
2.Jenkins 또는 CI 도구로 Job 실행




 
