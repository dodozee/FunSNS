package com.funs.gifticonservice.domain.gifticon.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GifticonListResponse {
    private List<GifticonResponse> gifticonList;
    private Page page;

    @Data @AllArgsConstructor
    static class Page{
        int startPage;
        int totalPate;

    }

    public GifticonListResponse(List<GifticonResponse> gifticonList, int startPage, int totalPage){
        this.gifticonList = gifticonList;
        this.page = new Page(startPage, totalPage);
    }



}
