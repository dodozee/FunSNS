package com.funs.gifticonservice.domain.gifticon.service;

import com.funs.gifticonservice.domain.gifticon.dto.GifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.RegisterGifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.UpdateGifticonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface GifticonService {
    void createGifticon(Long userId, RegisterGifticonDto registerGifticonDto) throws IOException;

    void updateGifticon(Long userId, Long gifticonId, UpdateGifticonDto updateGifticonDto) throws IOException;

    void deleteGifticon(Long userId, Long gifticonId);

    Page<GifticonDto> findGifticonList(String categoryName, Pageable pageable);

    GifticonDto getGifticon(Long gifticonId);
}
