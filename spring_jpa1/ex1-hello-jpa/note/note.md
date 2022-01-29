# note

- spring.io에서 boot doc에 가면 사용하는 spring boot 버전과 맞는 hibernate 버전을 확인할 수 있다고함
  - 이렇게 해야 spring boot와 jpa를 연동해서 활용가능
  - 현재 예제는 spring boot를 쓰진 않아서 그냥 진행
- h2 db 드라이버는 설치한 버전이랑 같은게 좋음. 안그러면 잘 안될때가 있음
  ```
          <dependency>
              <groupId>com.h2database</groupId>
              <artifactId>h2</artifactId>
              <version>1.4.199</version>
          </dependency>
  
  ```
- jpa를 쓸땐 persistence.xml을 "특정" 위치에 넣어 주어야함.
  - maven쓸땐 resources 밑에 code외 나머지를 넣으니까 여기에 META-INF를 추가
  - `hibernate.dialect` 이거 중요

- > 강의에 나온 코드가 pdf에 없는 case도 있음

- jpa에서 가장 중요한것은 모든 data 변경작업은 transaction안에서 이루어 져야한다. 
- 일단 table, field 매핑은 관례에 따라 자동으로 진행되는데, 직접 annotation으로 매핑할수도 있다.