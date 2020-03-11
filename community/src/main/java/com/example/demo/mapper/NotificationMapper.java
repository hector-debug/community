package com.example.demo.mapper;

import com.example.demo.model.NotificationModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outId,type,gmt_create,status) values (#{notified},#{receiver},#{outId},#{gmt_create},#{status}) ")
    void insert(NotificationModel notification);

    @Select("select count(1) from notification where receiver=#{receiver}")
    Integer countByReceiver(Integer receiver);
    @Select("select * from notification where receiver=#{userId} limit #{offset},#{size}")
    List<NotificationModel> getUserList(Integer userId, Integer offset, Integer size);
}
