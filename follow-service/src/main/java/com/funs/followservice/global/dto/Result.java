package com.funs.followservice.global.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private Code code;
    private String message;
    private T data;

    @Builder
    public Result(Code code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result createErrorResult(String message) {
        return Result.builder()
                .code(Code.ERROR)
                .message(message)
                .data(null)
                .build();

    }

    public static Result createSuccessResult(String message) {
        return Result.builder()
                .code(Code.SUCCESS)
                .message(message)
                .data(null)
                .build();

    }


    public static <T> Result createSuccessResult(T data){
        return Result.builder()
                .code(Code.SUCCESS)
                .message("")
                .data(data)
                .build();
    }

    public static <T> Result createSuccessResult(T data, String message){
        return Result.builder()
                .code(Code.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static Result success(){
        return Result.builder()
                .code(Code.SUCCESS)
                .message("성공")
                .data(null)
                .build();
    }


}
