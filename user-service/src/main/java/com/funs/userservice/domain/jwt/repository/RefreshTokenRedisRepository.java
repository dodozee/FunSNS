package com.funs.userservice.domain.jwt.repository;

import com.funs.userservice.domain.jwt.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
