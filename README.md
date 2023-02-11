# 판소리
### 프로젝트 소개

나홀로 소송을 도와주는 판례 요약 검색 서비스
- 부담스러운 변호사 수임료로 인한 나홀로 소송 준비자 증가
- 복잡한 소송 절차로 인해 나홀로 준비 과정에서 겪는 다양한 어려움들이 발생함
- 나홀로 소송을 돕기 위한 기능 제공

### 상세 설명
- Open Api를 통해 약 8만 4천건의 판례 데이터를 수집하여 My SQL를 활용한 데이터 베이스 구축
- 판례 검색 기능을 위해 JPA를 사용하여 판례 검색 기능 구현
    - 형태소 분석기를 사용하여 자연어 검색이 가능하도록 구현
    - 판례 데이터의 분량으로 인한 검색 성능 문제 발생
        - MySQL FullText 인덱싱 기능 적용을 통한 성능 개선 - 검색 속도 20% 향상
        - 추가 성능 향상을 위한 Elastic Search 적용
- 사용자의 검색 기록, 북마크, 소송 준비 과정 체크리스트 기능 구현
- AWS EC2를 통한 spring boot와 elastic search 서버 배포, RDS를 활용한 MySQL 데이터베이스 배포

### 💻 팀원
[고대훈](https://github.com/Hoony0321) | [이인환](https://github.com/dlsghks1227) | [이지혁](https://github.com/olzlgur)
|:---:|:---:|:---:|
![50730897](https://user-images.githubusercontent.com/77485914/218256143-6684db43-f79d-4b0a-b9a3-d42612a73663.png)|![49141887](https://user-images.githubusercontent.com/77485914/218256200-119c2981-9b29-4d68-9cc7-402b742f499c.png)|<img width="400px" src="https://github.com/olzlgur.png">

### 실행 화면
![%25EC%258A%25A4%25ED%2581%25AC%25EB%25A6%25B0%25EC%2583%25B7%25202022-10-06%2520%25EC%2598%25A4%25EC%25A0%2584%252012 11 54](https://user-images.githubusercontent.com/77485914/218255539-b503dbeb-e109-496d-91d4-2bfd42db5fd0.png)
<img width="1100" alt="Untitled-4" src="https://user-images.githubusercontent.com/77485914/218255548-9ac9f5ed-5e1f-42bf-a84c-ae517f1fda99.png">
<img width="1100" alt="Untitled-5" src="https://user-images.githubusercontent.com/77485914/218255560-0db3d925-2af1-478d-8256-97535b50f1c1.png">
<img width="1100" alt="Untitled-6" src="https://user-images.githubusercontent.com/77485914/218255570-7646fb54-19de-4fe9-a14c-48c85cbd4700.png">

### 시스테 구성도
<img width="1159" alt="Untitled-3" src="https://user-images.githubusercontent.com/77485914/218255332-1d50c93e-bd1e-401d-82cc-b5ad57a54f3d.png">

### ERD 설계
![%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-02-07%20%EC%98%A4%ED%9B%84%2011 53 23](https://user-images.githubusercontent.com/77485914/218255378-d6ddd685-a966-4c90-89ba-4ca96fb1065b.png)
