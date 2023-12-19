package com.funs.gifticonservice.domain.gifticon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGifticonDto {
    private String categoryName;
    private String gifticonName;
    private String description;
    private Long price;
    private Long amount;
    private MultipartFile image;
}
