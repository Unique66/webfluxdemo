package com.sxh.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author SXH
 * @description User
 * @date 2020/12/30 23:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String name;
    private String gender;
    private Integer age;
}
