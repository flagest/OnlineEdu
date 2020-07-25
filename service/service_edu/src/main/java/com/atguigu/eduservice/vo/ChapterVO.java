package com.atguigu.eduservice.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wu on 2020/7/20 0020
 */
@Data
public class ChapterVO {
    private String id;
    private String title;
    private List<VideoVO> children;
}
