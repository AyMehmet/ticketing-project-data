package com.cydeo.entity;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@
public class Role extends BaseEntity{

//    private Long id;
    private String description;

}
