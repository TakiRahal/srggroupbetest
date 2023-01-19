package com.takirahal.srfgroup.modules.category.controllers;

import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import com.takirahal.srfgroup.modules.category.services.CategoryService;
import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

//    @Autowired
//    private JobLauncher jobLauncher;
//
//
//    @Autowired
//    @Qualifier("categoryBeanJob")
//    private Job jobCategory;

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("public")
    public ResponseEntity<Page<CategoryDTO>> getAllPublicCategories(CategoryFilter criteria, Pageable pageable) {
        log.debug("REST request to get Categories by criteria: {}", criteria);
        Page<CategoryDTO> page = categoryService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping("admin/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to save Category : {}", categoryDTO);
        CategoryDTO result = categoryService.save(categoryDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("category.added_successfully", result.getId().toString()), HttpStatus.CREATED);
    }

    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("admin/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.info("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return new ResponseEntity<>(categoryDTO.get(), HttpStatus.OK);
    }


    /**
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping("admin/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to uodate Category : {}", categoryDTO);
        CategoryDTO result = categoryService.save(categoryDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("category.update_successfully", result.getId().toString()), HttpStatus.OK);
    }

    /**
     *
     * @param categoryDTOList
     * @return
     */
    @PutMapping("admin/update-index")
    public ResponseEntity<Boolean> updateIndexCategories(@RequestBody List<CategoryDTO> categoryDTOList){
        log.debug("REST request to uodate index Categories : {}", categoryDTOList);
        Boolean result = categoryService.updateIndexCategories(categoryDTOList);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("category.index_update_successfully", "true"), HttpStatus.OK);
    }


    /**
     *
     * @return
     */
//    @GetMapping("admin/import")
//    public ResponseEntity<BatchStatus> importCategories() {
//        try{
//            log.info("REST request to import Categories by data.sql");
//            Map<String, JobParameter<?>> parameters = new HashMap<>();
//
////            Map<String, JobParameter> parms = new HashMap<>();
////            parms.put("time", new JobParameter(System.currentTimeMillis()));
//            JobParameters jobParameter = new JobParameters(parameters);
//            JobExecution jobExecution = jobLauncher.run(jobCategory, jobParameter);
//            while (jobExecution.isRunning()){
//                System.out.println("...");
//            }
//            return new ResponseEntity<>(jobExecution.getStatus(), HttpStatus.OK);
//        }catch (Exception e){
//            log.error("Error to import Categories by data.sql : {}", e.getStackTrace());
//            return new ResponseEntity<>(BatchStatus.FAILED, HttpStatus.OK);
//        }
//    }
}
