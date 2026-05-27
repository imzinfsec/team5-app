package com.example.petcarelog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@Configuration
@EnableRedisIndexedHttpSession(redisNamespace = "petcarelog:session")
public class RedisSessionConfig {
}
