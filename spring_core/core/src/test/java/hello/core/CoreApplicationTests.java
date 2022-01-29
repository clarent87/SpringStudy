package hello.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

	// 이거 Test 띄우면, Source 코드에서 컨테이너 Bean까지는 자동으로 할당하는듯.
	// 즉 Test code를 짤때, AnnotationConfigApplicationContext 같은걸로 직접 등록 없어도 되는거 같다.
	// 이부분은 설명이 안되었음

	@Test
	void contextLoads() {
	}

}
