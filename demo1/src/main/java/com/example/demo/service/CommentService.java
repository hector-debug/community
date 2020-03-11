package com.example.demo.service;

import com.example.demo.dto.CommentShowDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.CommentModel;
import com.example.demo.model.NotificationModel;
import com.example.demo.model.Question;
import com.example.demo.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public String addComment(CommentModel commentModel) {

        if(commentModel.getParentId()==null || commentModel.getParentId()==0){
            return "";
        }
        if(commentModel.getType() == null ){
            return "";
        }

        if(commentModel.getType()== CommentTypeEnum.QUESTION.getType()){
            //回复评论
            commentMapper.addComment(commentModel);
            Integer parentId=commentModel.getParentId().intValue();
            commentMapper.updateCommentCount(parentId);
            //通过评论目标获取评论对象的id
           CommentModel commentTarget= commentMapper.getCommentById(commentModel.getParentId());
            //向该评论的创建人发送一个未读评论
//            creatNotify(commentModel,commentTarget.getCommentator(),1);
            return "回复成功";
        }else {
            //回复问题
            //通过评论目标获取评论对象的id
            Question question= questionMapper.getQuestionById(commentModel.getParentId().intValue());
            commentMapper.addComment(commentModel);
            //向该问题的创建人发送一个未读评论
           // creatNotify(commentModel,question.getId(),2);

            return "评论成功";
    }
}

private void creatNotify(CommentModel commentModel,Integer receiver,Integer type){
    NotificationModel notification =new NotificationModel();
    notification.setGmt_create(System.currentTimeMillis());
    notification.setType(NotificationEnum.REPLY_QUESTION.getType());
    notification.setOutId(commentModel.getParentId().intValue());
    notification.setNotifier(commentModel.getCommentator());
    notification.setType(type);
    notification.setReceiver(receiver);
    notificationMapper.insert(notification);
}

    public List<CommentShowDTO> getCommentByQuestionId(Integer id) {
        //得到该问题的所有评论
        List<CommentModel> comments=commentMapper.getCommentByQuestionId(id);

        if(comments.size()==0){
            return new ArrayList<>();
        }
        //得到评论者，且无重复
        Set<Integer> commentator=comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentator);
        List<UserModel> userModelList=new ArrayList<>();
        for(Integer userId:userIds){
            userModelList.add(userMapper.findbyid(userId));
        }
        Map<Integer,UserModel> userMap=userModelList.stream().collect(Collectors.toMap(user->user.getId(),user ->user));
        //绑定评论和评论者
        List<CommentShowDTO> commentShowDTOS=comments.stream().map(comment ->{
            CommentShowDTO commentShowDTO=new CommentShowDTO();
            BeanUtils.copyProperties(comment,commentShowDTO);
            commentShowDTO.setUserModel(userMap.get(comment.getCommentator()));
            return commentShowDTO;
        }).collect(Collectors.toList());

        //


        return commentShowDTOS;
    }
}
