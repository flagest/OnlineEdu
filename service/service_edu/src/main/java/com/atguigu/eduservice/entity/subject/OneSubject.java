package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu on 2020/7/18 0018
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //    private List<TwoSubject> children = new ArrayList<>();
//        private List<TwoSubject> children;
    private List<OneSubject> children;
}
