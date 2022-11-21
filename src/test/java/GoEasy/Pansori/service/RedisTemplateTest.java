package GoEasy.Pansori.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void redis_data_save() throws Exception {
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "testKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        Assertions.assertThat(value).isEqualTo("hello");
    }
}
