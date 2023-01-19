package com.takirahal.srfgroup.config.Batch;

import com.takirahal.srfgroup.modules.address.entities.Address;
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
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<Address> addressItemReader;

    @Autowired
    private ItemWriter<Address> addressItemWriter;

    @Autowired
    private ItemProcessor<Address, Address> addressItemProcess;

    @Bean
    @Qualifier("addressBeanJob")
    public Job addressJob(){
        Step step0 = stepBuilderFactory.get("address-step-load-data")
                .<Address, Address>chunk(100)
                .reader(addressItemReader)
                .processor(addressItemProcess)
                .writer(addressItemWriter)
                .build();
        return jobBuilderFactory.get("address-data-loader-job")
                .start(step0)
                .build();
    }

    @Bean
    public FlatFileItemReader<Address> addressBeanFlatFileItemReader(@Value("${inputFile}") Resource inputFile){
        FlatFileItemReader<Address> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("FFIR1");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(addressBeanLineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<Address> addressBeanLineMapper() {
        DefaultLineMapper<Address> bankTransactionLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "city", "lat", "lng", "country", "iso2", "admin_name", "capital", "population", "population_proper");
        bankTransactionLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Address.class);
        bankTransactionLineMapper.setFieldSetMapper(fieldSetMapper);
        return bankTransactionLineMapper;
    }

    @Bean
    public ItemProcessor<Address, Address> addressBeanItemProcessor(){
        return new ItemProcessor<Address, Address>() {
            @Override
            public Address process(Address address) throws Exception {
                return null;
            }
        };
    }
}
