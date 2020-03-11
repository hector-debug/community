package com.example.demo.cache;

import com.example.demo.dto.TagDTO;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> getTags() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "c", "c++", "php", "perl", "python", "javascript", "c#", "ruby", "go", "lua", "node.js", "scala", "bash", "typescript", "flutter"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("python开发");
        framework.setTags(Arrays.asList( "python","List","django","flask","tornado","web.py","sqlalchemy","virtualenv"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList( "linux","unix","ubuntu","windows-server","centos","apache","nginx","负载均衡"));
        tagDTOS.add(server);


        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList( "数据库","mysql","sqlite","oracle","sql","mongodb","memcached","redis","nosql"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList( "vim","emacs","ide","eclipse","xcode","docker","github","intellij-idea","visual-studio"));
        tagDTOS.add(tool);
        return tagDTOS;

    }

    public static String isValid(String tags){
        String[] split= StringUtils.split(tags,",");
        List<TagDTO> tagDTOS=getTags();
        List<String> tagList=tagDTOS.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
        String invalid=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}