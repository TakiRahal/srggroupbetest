package com.takirahal.srfgroup.config.Batch;


import com.takirahal.srfgroup.modules.category.entities.Category;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

// @Configuration
// @EnableBatchProcessing
public class SpringBatchConfigCategory {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<Category> categoryItemReader;

    @Autowired
    private ItemWriter<Category> categoryItemWriter;

    @Autowired
    private ItemProcessor<Category, Category> categoryItemProcess;


    @Bean
    @Qualifier("categoryBeanJob")
    public Job categoryJob(){
        Step step0 = stepBuilderFactory.get("category-step-load-data")
                .<Category, Category>chunk(100)
                .reader(categoryItemReader)
                .processor(categoryItemProcess)
                .writer(categoryItemWriter)
                .build();
        return jobBuilderFactory.get("category-data-loader-job")
                .start(step0)
                .build();
    }

    @Bean
    public FlatFileItemReader<Category> categoryBeanFlatFileItemReader(@Value("${inputFileCategory}") Resource inputFile){
        FlatFileItemReader<Category> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("CATEGORY_FFIR1");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(categoryBeanLineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<Category> categoryBeanLineMapper() {
        DefaultLineMapper<Category> bankTransactionLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "titleAr", "titleFr", "titleEn","index");
        bankTransactionLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Category.class);
        bankTransactionLineMapper.setFieldSetMapper(fieldSetMapper);
        return bankTransactionLineMapper;
    }

    @Bean
    public ItemProcessor<Category, Category> categoryBeanItemProcessor(){
        return new ItemProcessor<Category, Category>() {
            @Override
            public Category process(Category category) throws Exception {
                return category;
            }
        };
    }
}
