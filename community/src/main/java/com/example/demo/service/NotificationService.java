//package com.example.demo.service;
//
//import com.example.demo.dto.NotificationDTO;
//import com.example.demo.dto.PaginationDTO;
//import com.example.demo.mapper.NotificationMapper;
//import com.example.demo.model.NotificationModel;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NotificationService {
//    @Autowired
//    private NotificationMapper notificationMapper;
//
//    public PaginationDTO getNotifyList(Integer userId, Integer page, Integer size) {
//
//        //得到偏移量，实现分页功能
//        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
//
//        Integer totalPage;
//        Integer totalCount=notificationMapper.countByReceiver(userId);
//        if(totalCount % size == 0 ){
//            totalPage =totalCount / size;
//        }else {
//            totalPage =totalCount / size +1;
//        }
//        //根据总数，页数，页面大小计算并插入页面元素
//        if(page<1) page=1;
//        if (page>totalPage) page= totalPage;
//
//        paginationDTO.setPagination(totalPage,page);
//        Integer offset=size*(page-1);
//        List<NotificationDTO> notificationDTOS=new ArrayList<>();
//        List<NotificationModel> questions=notificationMapper.getUserList(userId,offset,size);
//
//
//        paginationDTO.setQuestionDTOS(notificationDTOS);
//
//        return  paginationDTO;
//    }
//}
