package com.example.demo.mapper;

import com.example.demo.model.CommentModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void addComment(CommentModel commentModel);
    @Select("select * from comment where parent_id=#{id} and type =1 ORDER BY  gmt_create DESC")
    List<CommentModel> getCommentByQuestionId(Integer id);
    @Update("update question set comment_count=comment_count+1 where id=#{parentId}")
    void updateCommentCount(Integer parentId);
    @Select("select * from comment where id=#{id}")
    CommentModel getCommentById(Long id);
}
