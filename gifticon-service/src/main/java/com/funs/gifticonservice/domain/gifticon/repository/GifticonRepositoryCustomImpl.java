package com.funs.gifticonservice.domain.gifticon.repository;

import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.gifticonservice.domain.gifticon.entity.QGifticon.gifticon;


@Slf4j
@Repository
@RequiredArgsConstructor
public class GifticonRepositoryCustomImpl implements GifticonRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Gifticon> getGifticonList(Pageable pageable, String categoryName){

        Long count = queryFactory.select(gifticon.count())
                .from(gifticon)
                .where(gifticon.categoryName.eq(categoryName))
                .fetchOne();

        List<Gifticon> gifticons = queryFactory
                .selectFrom(gifticon)
                .where(gifticon.categoryName.eq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();



        return PageableExecutionUtils.getPage(gifticons, pageable, () -> count);
    }
}
