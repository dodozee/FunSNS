package com.funs.gifticonservice.domain.gifticon.service;

import com.funs.gifticonservice.domain.gifticon.dto.GifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.RegisterGifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.UpdateGifticonDto;
import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import com.funs.gifticonservice.domain.gifticon.exception.NotExistGifticonException;
import com.funs.gifticonservice.domain.gifticon.repository.GifticonRepository;
import com.funs.gifticonservice.domain.gifticon.repository.GifticonRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GifticonServiceImpl implements GifticonService{

    private final GifticonRepository gifticonRepository;
    private final GifticonRepositoryCustom gifticonRepositoryCustom;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public void createGifticon(Long userId, RegisterGifticonDto registerGifticonDto) throws IOException {
        Gifticon gifticon = Gifticon.builder()
                .name(registerGifticonDto.getGifticonName())
                .categoryName(registerGifticonDto.getCategoryName())
                .description(registerGifticonDto.getDescription())
                .price(registerGifticonDto.getPrice())
                .amount(registerGifticonDto.getAmount())
                .build();
        Gifticon save = gifticonRepository.save(gifticon);
        String imageUrl = s3Uploader.upload(save.getId(), registerGifticonDto.getImage(), "static");
        gifticon.setImage(imageUrl);

    }

    @Transactional
    @Override
    public void updateGifticon(Long userId, Long gifticonId, UpdateGifticonDto updateGifticonDto) throws IOException {
        Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘 입니다."));

        if(!updateGifticonDto.getImage().isEmpty()) {
            String updateImage = s3Uploader.upload(gifticonId, updateGifticonDto.getImage(), "static");
            gifticon.setImage(updateImage);
        }

        gifticon.updateGifticon(updateGifticonDto.getCategoryName(), updateGifticonDto.getGifticonName(), updateGifticonDto.getDescription(), updateGifticonDto.getPrice(), updateGifticonDto.getAmount());

    }

    @Transactional
    @Override
    public void deleteGifticon(Long userId, Long gifticonId) {
        Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘 입니다."));       
        gifticonRepository.delete(gifticon);
    }

    @Override
    public Page<GifticonDto> findGifticonList(String categoryName, Pageable pageable) {

        Page<Gifticon> gifticonList = gifticonRepositoryCustom.getGifticonList(pageable, categoryName);

        return PageableExecutionUtils.getPage(gifticonList.stream()
                .map(GifticonDto::of)
                .collect(Collectors.toList()), pageable, gifticonList::getTotalElements);
    }

    @Override
    public GifticonDto getGifticon(Long gifticonId) {
        Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘 입니다."));

        return GifticonDto.of(gifticon);
    }
}
