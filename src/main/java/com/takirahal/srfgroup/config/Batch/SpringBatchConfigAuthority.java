package com.takirahal.srfgroup.config.Batch;

import com.takirahal.srfgroup.modules.user.entities.Authority;
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
public class SpringBatchConfigAuthority {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<Authority> authorityItemReader;

    @Autowired
    private ItemWriter<Authority> authorityItemWriter;

    @Autowired
    private ItemProcessor<Authority, Authority> authorityItemProcess;

    @Bean
    @Qualifier("authorityBeanJob")
    public Job authorityJob(){
        Step step0 = stepBuilderFactory.get("authority-step-load-data")
                .<Authority, Authority>chunk(100)
                .reader(authorityItemReader)
                .processor(authorityItemProcess)
                .writer(authorityItemWriter)
                .build();
        return jobBuilderFactory.get("authority-data-loader-job")
                .start(step0)
                .build();
    }

    @Bean
    public FlatFileItemReader<Authority> authorityBeanFlatFileItemReader(@Value("${inputFileAuthority}") Resource inputFile){
        FlatFileItemReader<Authority> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("FFIR1");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(authorityBeanLineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<Authority> authorityBeanLineMapper() {
        DefaultLineMapper<Authority> bankTransactionLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("name");
        bankTransactionLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Authority.class);
        bankTransactionLineMapper.setFieldSetMapper(fieldSetMapper);
        return bankTransactionLineMapper;
    }

    @Bean
    public ItemProcessor<Authority, Authority> authorityBeanItemProcessor(){
        return new ItemProcessor<Authority, Authority>() {
            @Override
            public Authority process(Authority address) throws Exception {

                return null;
            }
        };
    }
}
