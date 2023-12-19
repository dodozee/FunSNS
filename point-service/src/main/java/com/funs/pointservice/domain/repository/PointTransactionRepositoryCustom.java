package com.funs.pointservice.domain.repository;

import com.funs.pointservice.domain.entity.PointTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointTransactionRepositoryCustom {

    Page<PointTransaction> getPointTransactionHistoryByUserId(Long userId, Pageable pageable);
}
