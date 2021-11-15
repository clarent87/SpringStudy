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