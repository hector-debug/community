package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO getList(String search,Integer page, Integer size) {

        Integer totalPage=0;
        Integer totalCount=0;
        if(!StringUtils.isEmpty(search)){
            String[] searchs=StringUtils.split(search," ");
           String regexpSearch=Arrays.stream(searchs).collect(Collectors.joining("|"));
            totalCount =questionMapper.getSearchCount(search);
        }else {
            totalCount=questionMapper.count();
        }

        if(totalCount % size == 0 ){
            totalPage =totalCount / size;
        }else {
            totalPage =totalCount / size +1;
        }
        //根据总数，页数，页面大小计算并插入页面元素

        if (page>totalPage) page= totalPage;
        if(page<1) page=1;
        //得到偏移量，实现分页功能
        Integer offset=size*(page-1);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        List<Question> questions=new ArrayList<>();
        if(!StringUtils.isEmpty(search)){
            String[] searchs=StringUtils.split(search," ");
            String regexpSearch=Arrays.stream(searchs).collect(Collectors.joining("|"));
             questions=questionMapper.getSearchList(regexpSearch,offset,size);
        }else {
          questions=questionMapper.getList(offset,size);
        }

        PaginationDTO paginationDTO = new PaginationDTO();



        paginationDTO.setPagination(totalPage,page);


        for (Question question:questions){
            UserModel userModel=userMapper.findbyid(question.getCreator());

            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUserModel(userModel);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestionDTOS(questionDTOS);
        return  paginationDTO;
    }

    public PaginationDTO userList(Integer userId, Integer page, Integer size) {
        //得到偏移量，实现分页功能
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount=questionMapper.countByUserId(userId);
        if(totalCount % size == 0 ){
            totalPage =totalCount / size;
        }else {
            totalPage =totalCount / size +1;
        }
        //根据总数，页数，页面大小计算并插入页面元素
        if(page<1) page=1;
        if (page>totalPage) page= totalPage;

        paginationDTO.setPagination(totalPage,page);
        Integer offset=size*(page-1);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        List<Question> questions=questionMapper.getUserList(userId,offset,size);
        for (Question question:questions){
            UserModel userModel=userMapper.findbyid(question.getCreator());
            System.out.println(userModel);
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUserModel(userModel);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestionDTOS(questionDTOS);

        return  paginationDTO;
    }

    public QuestionDTO getQuestionByID(Integer id) {
       Question question= questionMapper.getQuestionById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserModel userModel=userMapper.findbyid(question.getCreator());
        questionDTO.setUserModel(userModel);
        return  questionDTO;

    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);

        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void incView(Integer id) {

        questionMapper.updateViewById(id);
    }

    public List<QuestionDTO> getRelated(QuestionDTO questionDTO) {
        if(questionDTO.getTag()==null){
            return new ArrayList<>();
        }
        String[] tags= StringUtils.split(questionDTO.getTag(),",");
        if(tags==null){
            return new ArrayList<>();
        }

        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions=questionMapper.getRelated(question);
        List<QuestionDTO> questionDTOS=questions.stream().map(q->{
            QuestionDTO questionDTO1=new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

//得到热门问题
    public List<Question> getHots() {
        List<Question> hots=questionMapper.getHots();
        return  hots;

    }
}
