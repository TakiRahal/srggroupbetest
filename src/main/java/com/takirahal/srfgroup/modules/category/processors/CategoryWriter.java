package com.takirahal.srfgroup.modules.category.processors;

import com.takirahal.srfgroup.modules.category.entities.Category;
import com.takirahal.srfgroup.modules.category.repositories.CategoryRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryWriter implements ItemWriter<Category> {

//    @Autowired
//    CategoryRepository categoryRepository;
//
//    @Override
//    public void write(List<? extends Category> list){
//
//        list.stream().forEach(category -> {
//            categoryRepository.save(category);
//        });
//    }

    @Override
    public void write(Chunk<? extends Category> chunk) throws Exception {

    }
}
