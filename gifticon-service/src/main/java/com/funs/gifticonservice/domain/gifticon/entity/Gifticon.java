package com.funs.gifticonservice.domain.gifticon.entity;

import com.funs.gifticonservice.domain.gifticon.exception.NotEnoughGifticonStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "gifticon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gifticon {

    @Id @GeneratedValue
    @Column(name = "gifticon_id")
    private Long id;

    private String name;

    private Long price;

    private Long amount;

    private String description;

    private String image;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
    private String categoryName;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;


//    public void setCategory(Category category) {
//        this.category = category;
//    }

    public void setImage(String image) {
        this.image = image;
    }

    @Builder
    public static Gifticon createGifticon(String name, Long price, Long amount, String description, String categoryName) {
        Gifticon gifticon = new Gifticon();
        gifticon.name = name;
        gifticon.price = price;
        gifticon.amount = amount;
        gifticon.description = description;
        gifticon.categoryName = categoryName;
        return gifticon;
    }

    public void updateGifticon(String categoryName, String gifticonName, String description, Long price, Long amount) {
        this.categoryName= categoryName;
        this.name = gifticonName;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    //TODO 기프티콘 감소 처리
    public void decreaseAmount(Long amount) {
        if(this.amount >= amount && amount > 0)
            this.amount -= amount;
        else{
            throw new NotEnoughGifticonStockException("기프티콘의 재고가 부족합니다.");
        }
    }

    public void increaseAmountByCompensation(Long amount) {
            this.amount += amount;
    }
}
