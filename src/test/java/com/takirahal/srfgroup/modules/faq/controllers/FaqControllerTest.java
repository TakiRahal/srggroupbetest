package com.takirahal.srfgroup.modules.faq.controllers;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
import com.takirahal.srfgroup.modules.faq.services.impl.FaqServiceImpl;
import com.takirahal.srfgroup.modules.utils.TestUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Exist Autowired
@AutoConfigureMockMvc
class FaqControllerTest {

    private static final String ENTITY_API_URL = "/api/faq/";

    @MockBean
    FaqService faqService;

    @Spy
    private FaqMapper faqMapper = Mappers.getMapper(FaqMapper.class);

    @Autowired
    private MockMvc restFaqMockMvc;

    @InjectMocks
    private FaqServiceImpl faqServiceImpl;

    @Before(value = "")
    public void init() {
        ReflectionTestUtils.setField(faqMapper , "faqServiceImpl", faqServiceImpl);
    }

    final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzcmZncm91cC5jb250YWN0QGdtYWlsLmNvbSIsImlhdCI6MTY1NDI4OTI0NCwiZXhwIjoxNjU0ODk0MDQ0fQ.OSDB0jr22qCoFFvuqywHKsQDmmFifACwLd06ZH84w7eWDIwJutiU5_c3_W2qg-eBlxsCM_bBLaEYxlOzYEYCVw";

    final FaqDTO faqDTO0 = FaqDTO.builder().id(0L).questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();
    final FaqDTO faqDTO1 = FaqDTO.builder().id(1L).questionAr("Qar1").responseAr("Rar1").questionFr("Qfr1").responseFr("Rfr1").questionEn("Qen1").responseEn("Ren1").build();
    final FaqDTO faqDTO2 = FaqDTO.builder().id(2L).questionAr("Qar2").responseAr("Rar2").questionFr("Qfr2").responseFr("Rfr2").questionEn("Qen2").responseEn("Ren2").build();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createFaq() throws Exception {

        // Given
        Faq faq = Faq.builder().questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();
        FaqDTO faqDTO = faqMapper.toDto(faq);

        // When
        ResultActions mvcResult = restFaqMockMvc
                .perform(
                        post(ENTITY_API_URL+"admin/create")
                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(faqDTO))
                        .header("Authorization", "Bearer "+token)
                );

        // Then
        mvcResult.andExpect(status().isCreated());
        Assertions.assertTrue(faqService.findAll().size()>0); // Verify save in DB

    }

    @Test
    @DisplayName("Should list all posts when making GET equest to endpoint - /api/faq/public")
    void getAllPublicFaqs() throws Exception {

        // Given
        Pageable pageable = PageRequest.of(0, 2);
        List<FaqDTO> faqDtoList = new ArrayList<>();
        faqDtoList.add(faqDTO0);
        faqDtoList.add(faqDTO1);
        faqDtoList.add(faqDTO2);
        Page<FaqDTO> faqDtoPage = new PageImpl<>(faqDtoList, pageable, faqDtoList.size());

        Mockito.when(faqService.findByCriteria(any(FaqFilter.class), any(Pageable.class))).thenReturn(faqDtoPage);

        // When
        // Execute the GET request
        MvcResult mvcResult = restFaqMockMvc.perform( MockMvcRequestBuilders
                .get(ENTITY_API_URL+"public")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String content = mvcResult.getResponse().getContentAsString();
        assertFalse(content.isEmpty());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

//        MvcResult mvcResult = restFaqMockMvc.perform(MockMvcRequestBuilders.get(ENTITY_API_URL+"public")
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String content = mvcResult.getResponse().getContentAsString();
                // Validate the response code and content type
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
                // .andExpect(jsonPath("$.[*].id").value(hasItem(faq.getId().intValue())));
                // .andExpect(jsonPath("$.[*].questionAr").value(hasItem("Qar")));

                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/api/faq/public"))

                // Validate the returned fields
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is("Widget Name")))
//                .andExpect(jsonPath("$[0].description", is("Description")))
//                .andExpect(jsonPath("$[0].version", is(1)))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].name", is("Widget 2 Name")))
//                .andExpect(jsonPath("$[1].description", is("Description 2")))
//                .andExpect(jsonPath("$[1].version", is(4)));

    }
}
