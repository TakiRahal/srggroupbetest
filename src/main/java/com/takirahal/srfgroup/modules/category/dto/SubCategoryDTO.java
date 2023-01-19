package com.takirahal.srfgroup.modules.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO implements Serializable {
    private Long id;

    private String titleAr;

    private String titleFr;

    private String titleEn;

    private CategoryDTO category;
}
