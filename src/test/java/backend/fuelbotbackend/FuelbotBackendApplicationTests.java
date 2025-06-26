package backend.fuelbotbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients(basePackages = "backend.fuelbotbackend.core.api")
class FuelbotBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
