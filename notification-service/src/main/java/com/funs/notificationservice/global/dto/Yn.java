package com.funs.notificationservice.global.dto;

import lombok.Getter;

@Getter
public enum Yn {
    Y(true), N(false);

    private boolean yn;

    Yn(boolean y) {
        this.yn = y;
    }
}
