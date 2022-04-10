# JPA 기초
[유튜브 채널](https://www.youtube.com/c/최범균) JPA 기초 영상에서 사용하는 예제 코드 

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
