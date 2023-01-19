package com.takirahal.srfgroup.modules.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private Long id;
    private String titleAr;
    private String titleFr;
    private String titleEn;
    private String imageContent;
    private Integer index;
}
