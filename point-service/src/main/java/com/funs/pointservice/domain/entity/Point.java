package com.funs.pointservice.domain.entity;

import com.funs.pointservice.domain.exception.NotEnoughBalanceException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String nickname;

    //0포인트 아래로 떨어질 수 없음
    private Long balance;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL)
    private List<PointTransaction> pointTransactions = new ArrayList<>();

    public void addPointTransaction(PointTransaction pointTransaction){
        this.pointTransactions.add(pointTransaction);
        pointTransaction.setPoint(this);
    }

    public static Point init(Long userId, String nickname, Long balance) {
        Point point = new Point();
        point.userId = userId;
        point.nickname = nickname;
        point.balance = balance;
        point.addPointTransaction(PointTransaction.init(userId, 0, "첫 가입 1000 포인트", balance, balance));
        return point;
    }

    public void addBalance(Long amount) {
        this.balance += amount;
    }

    public void subtractBalance(Long amount) {
        if(this.balance < amount) {
            throw new NotEnoughBalanceException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }

    public void increasePoint(Long amount, String description){
        addBalance(amount);
        this.addPointTransaction(PointTransaction.of(this.userId, 0, description,  amount, this.balance));

    }

    public void decreasePoint(Long amount, String description) {
      subtractBalance(amount);
      this.addPointTransaction(PointTransaction.of(this.userId, 1, description, amount, this.balance));
    }

    public void updateUserNickname(String nickname) {
        this.nickname = nickname;
    }

}
