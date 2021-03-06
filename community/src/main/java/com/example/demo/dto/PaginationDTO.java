package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirst;
    private boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages=new ArrayList<>();

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
       this.currentPage=page;

        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page -i>0){
                pages.add(0,page-i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }


        //是否展示上一页
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        //是否展示下一页
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true ;
        }
         //是否展示第一页
        if(pages.contains(1)){
            showFirst = false;
        }else {
            showFirst = true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
