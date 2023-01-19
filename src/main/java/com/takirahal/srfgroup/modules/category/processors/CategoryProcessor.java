package com.takirahal.srfgroup.modules.category.processors;

import com.takirahal.srfgroup.modules.category.entities.Category;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CategoryProcessor implements ItemProcessor<Category, Category> {
    // private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public Category process(Category category) {
        // bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return category;
    }
}