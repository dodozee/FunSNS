package com.funs.gifticonservice.domain.gifticon.dto;

import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GifticonDto {
    private Long gifticonId;
    private String gifticonName;
    private String description;
    private Long price;
    private Long amount;
    private String image;

    public static GifticonDto of(Gifticon gifticon){
        return GifticonDto.builder()
                .gifticonId(gifticon.getId())
                .gifticonName(gifticon.getName())
                .description(gifticon.getDescription())
                .price(gifticon.getPrice())
                .amount(gifticon.getAmount())
                .image(gifticon.getImage())
                .build();
    }
}
