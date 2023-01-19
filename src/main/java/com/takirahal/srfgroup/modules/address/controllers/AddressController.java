package com.takirahal.srfgroup.modules.address.controllers;

import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.address.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/address/")
public class AddressController {

    private final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService addressService;

//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    @Qualifier("addressBeanJob")
//    private Job jobAddress;

    /**
     *
     * @param addressDTO
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<AddressDTO>> getAllPublicAddresses(AddressDTO addressDTO, Pageable pageable) {
        log.info("REST request to get Addresses by criteria: {}", addressDTO);
        Page<AddressDTO> page = addressService.findByCriteria(addressDTO, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     *
     * @return
     */
//    @GetMapping("admin/import")
//    public ResponseEntity<BatchStatus> importAddresses() {
//        try{
//            log.info("REST request to import Addresses by data.sql");
//            Map<String, JobParameter<?>> parameters = new HashMap<>();
//
//            // Map<String, JobParameter> parms = new HashMap<>();
//            // parms.put("time", new JobParameter(System.currentTimeMillis()));
//            JobParameters jobParameter = new JobParameters(parameters);
//            JobExecution jobExecution = jobLauncher.run(jobAddress, jobParameter);
//            while (jobExecution.isRunning()){
//                System.out.println("...");
//            }
//            return new ResponseEntity<>(jobExecution.getStatus(), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(BatchStatus.FAILED, HttpStatus.OK);
//        }
//    }
}
