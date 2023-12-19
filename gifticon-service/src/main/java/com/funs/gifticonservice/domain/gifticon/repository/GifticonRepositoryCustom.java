package com.funs.gifticonservice.domain.gifticon.repository;

import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GifticonRepositoryCustom {

    Page<Gifticon> getGifticonList(Pageable pageable, String categoryName);
}
