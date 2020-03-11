package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question ORDER BY  gmt_modified  limit #{offset},#{size}")
    List<Question> getList(@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> getUserList(@Param("userId")Integer id, @Param("offset") Integer offset, @Param("size")Integer size);
    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param("userId")Integer id);

    @Select("select * from question where id=#{id} ")
    Question getQuestionById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id =#{id}")
    void update(Question question);
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateViewById(Integer id);
    @Select("select view_count from question where id =#{id}")
    Integer findViewById(Integer id);

    @Select("select * from question where tag regexp #{tag} and id !=#{id}")
    List<Question> getRelated(Question question);

    @Select("select count(1) from question where title regexp #{search}")
    Integer getSearchCount(String search);

    @Select("select * from question where description regexp #{regexpSearch} OR title regexp #{regexpSearch}  limit #{offset},#{size}")
    List<Question> getSearchList(String regexpSearch, Integer offset, Integer size);

    @Select("select * from question ORDER BY view_count DESC  limit 0,10")
    List<Question> getHots();
}
