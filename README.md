# JPA 기초 & Spring Data JPA 기초
[유튜브 채널](https://www.youtube.com/c/최범균) JPA 기초/Spring Data JPA 기초 영상에서 사용하는 예제 코드 

## JPA 기초 내용

* JPA 기초 01 일단 해보기: https://youtu.be/Zwq2McbFOn4
* JPA 기초 02 코드 구조, 영속 컨텍스트: https://youtu.be/7ljqL8ThUts
* JPA 기초 03 엔티티 CRUD 처리: https://youtu.be/kmCKAwOie_I
* JPA 기초 04 엔티티 매핑: https://youtu.be/SbMJVuv8Iyo
* JPA 기초 05 엔티티 식별자 생성 방식: https://youtu.be/Xw9uTs72SVo
* JPA 기초 06 @Embeddable: https://youtu.be/WtS5IszIueA
* JPA 기초 07 @Embeddable 다른 테이블에 매핑하기: https://youtu.be/3_sdQGfL2Lg
* JPA 기초 08 값 콜렉션 Set 매핑: https://youtu.be/lQ4-kVeHVGk 
* JPA 기초 09 값 콜렉션 List 매핑 : https://youtu.be/Wq4B5RpIeAY
* JPA 기초 10 값 콜렉션 Map 매핑: https://youtu.be/CPIgicoqLnM
* JPA 기초 11 값 콜렉션 주의사항: https://youtu.be/yK4Avtxqz-k 
* JPA 기초 12 영속 컨텍스트 & 라이프사이클: https://youtu.be/tUwg78VkWJ0 
* JPA 기초 13 엔티티 연관 매핑 시작에 앞서: https://youtu.be/rZZSYG__8Jc
* JPA 기초 14 엔티티 간 1-1 단방향 연관 매핑: https://youtu.be/BhVzS90Ep78
* JPA 기초 15 엔티티 간 N-1 단방향 연관 매핑: https://youtu.be/i8XAqCGcLqw 
* JPA 기초 16 엔티티 간 1-N 단방향 연관 매핑: https://youtu.be/LAA8ICFS8bs
* JPA 기초 17 영속성 전파 & 연관 고려사항: https://youtu.be/7JAoNNhvsjw
* JPA 기초 18 JPQL 소개: https://youtu.be/UtEhC68GTH0
* JPA 기초 19 Criteria 소개: https://youtu.be/ZAiH382rUF0
* JPA 기초 20 기타 AttributeConverter, @Formula, @DynamicUpdate(@DynamicInsert), @Immutable, @Subselect: https://youtu.be/deJnCTkjLyU

## Spring Data JPA 기초 내용

* Spring Data JPA 01 시작하기: https://youtu.be/1Q3Qtd5HZy4
* Spring Data JPA 02 리포지터리 메서드 작성 규칙: https://youtu.be/qTiHaxVc6GY

## 로컬 환경 구성

### MySQL 설치

#### 직접 설치
다음 사이트에서 환경에 맞는 설치 파일 받아서 설치

* https://dev.mysql.com/downloads/

#### 도커 이용 설치
컨테이너 생성: 
```
docker create --name mysql8 -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:8.0.27
```
MYSQL_ROOT_PASSWORD 환경변수는 DB root 사용자 암호를 지정하는데 위 명령어는 root 암호로 root를 사용

컨테이너 시작:
```
docker start mysql8
```

### 데이터베이스와 계정 생성

root 사용자로 MySQL에 접속한 뒤에 jpabegin DB와 jpauser 사용자 생성

```
create database jpabegin CHARACTER SET utf8mb4;

CREATE USER 'jpauser'@'localhost' IDENTIFIED BY 'jpapass';
CREATE USER 'jpauser'@'%' IDENTIFIED BY 'jpapass';

GRANT ALL PRIVILEGES ON jpabegin.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpabegin.* TO 'jpauser'@'%';
```
