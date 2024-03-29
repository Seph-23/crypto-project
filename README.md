# crypto-project

## 요구사항
  - 거래소 코인 가격 비교 차트 (업비트, 바이낸스)
  - 백데이터 이용해서 가격 예측 
  - 레디스 캐싱 사용 데이터 처리 속도 향상
  - 웹서버, WAS 분리
  - 사용자 패턴
  - 서버 부하 테스트

  - 코인 가격
  - 주식 가격
  - 대표 환율 정보 (달러, 원, 파운드)
    - https://www.koreaexim.go.kr/ir/HPHKIR020M01?apino=2&viewtype=C&searchselect=&searchword=
  - 지금까지 우리가 예측했던 수치가 얼마나 일치했는지 결과 보여주기



## 회의록
  - 업비트, 바이낸스, 빗썸에 모두 존재하는 코인들 추출
    - 과거 3년치 데이터 디비에 누적 (daily price)
    - 카테고리 별로 분류 
  - 나스닥, 코스피 지수 일별 데이터
    - 대표주들 추출
    - 카테고리 별로 분류

  - 스케줄러로 당일 데이터 시간마다 업데이트
    - 나스닥, 코스피 장이 닫히는 시간에 최종값 입력 (아니면 그냥 계속 요청)
    - 우리가 생각하는 분석 결과 (통계, 머신러닝)
      - 마리아디비에서 데이터 추출후 분석 -> 레디스에 저장
    - MyBatis

  - REST API 서버 생성 (스프링)
    - JPA
  
  - Front
    - React
    - Typescript
    - Next.js (고려)

  - WebServer
    - NGINX

  - 업비트, 바이낸스, 빗썸에 모두 존재하는 코인들 추출
    - 과거 3년치 데이터 디비에 누적 (daily price)
    - 카테고리 별로 분류 
  - 나스닥, 코스피 지수 일별 데이터
    - 대표주들 추출
    - 카테고리 별로 분류

  - 우선 데이터 주는대로 디비에 저장 (3년치)

  - 프로젝트 구조 설정 
    - naming rule (camel case)
    - 프로퍼티 암호화 (jasypt)

  - 로그 파일 남기기 
    - 데이터 가져올때 로그 기록 (ex: 거래소 - 요청 시간)

  - 엔티티 pk를 coin 이름이랑 candleDateTime (UTC) 으로 변경
    - coin, opentime, price (binance) 
    - coin, candledatetime, price (bithumb) 
    - coin, candledatetimeutc, price (upbit)
  - alphavantage API 
    - 어떤 데이터를 활용할수 있을지
  - 스케줄러 실시간 데이터 업데이트
  - 분석 서버
    - myBatis 데이터 읽어와서 분석
  - API 서버 구조
    - 토큰 -> 레디스 저장
