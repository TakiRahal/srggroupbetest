package com.takirahal.srfgroup.config.Batch;

import com.takirahal.srfgroup.modules.user.entities.User;
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
public class SpringBatchConfigUser {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<User> userItemReader;

    @Autowired
    private ItemWriter<User> userItemWriter;

    @Autowired
    private ItemProcessor<User, User> userItemProcess;

    @Bean
    @Qualifier("userBeanJob")
    public Job userJob(){
        Step step0 = stepBuilderFactory.get("user-step-load-data")
                .<User, User>chunk(100)
                .reader(userItemReader)
                .processor(userItemProcess)
                .writer(userItemWriter)
                .build();
        return jobBuilderFactory.get("user-data-loader-job")
                .start(step0)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> userBeanFlatFileItemReader(@Value("${inputFileUser}") Resource inputFile){
        FlatFileItemReader<User> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("FFIR1");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(userBeanLineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<User> userBeanLineMapper() {
        DefaultLineMapper<User> bankTransactionLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "firstName", "lastName", "username", "email", "activated", "imageUrl", "activationKey", "resetKey", "phone", "sourceConnectedDevice", "password", "langKey", "linkProfileFacebook");
        bankTransactionLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(User.class);
        bankTransactionLineMapper.setFieldSetMapper(fieldSetMapper);
        return bankTransactionLineMapper;
    }

    @Bean
    public ItemProcessor<User, User> userBeanItemProcessor(){
        return new ItemProcessor<User, User>() {
            @Override
            public User process(User address) throws Exception {
                return null;
            }
        };
    }
}
