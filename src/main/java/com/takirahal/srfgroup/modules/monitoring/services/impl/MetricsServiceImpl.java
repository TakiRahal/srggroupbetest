package com.takirahal.srfgroup.modules.monitoring.services.impl;

import com.takirahal.srfgroup.modules.monitoring.dto.MetricsDto;
import com.takirahal.srfgroup.modules.monitoring.mapper.MetricsMapper;
import com.takirahal.srfgroup.modules.monitoring.models.Metrics;
import com.takirahal.srfgroup.modules.monitoring.services.MetricsService;
import com.takirahal.srfgroup.restclient.RestTemplateClientProvider;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsServiceImpl implements MetricsService {

    private final Logger log = LoggerFactory.getLogger(MetricsServiceImpl.class);

    @Autowired
    RestTemplateClientProvider restTemplateClientProvider;

    @Autowired
    MetricsMapper metricsMapper;

    @Autowired
    Tracer tracer; //

    final String[] listUrlMetrics = {"api/management/metrics/jvm.memory.used",
    "api/management/metrics/process.cpu.usage", "api/management/metrics/system.cpu.usage"};

    @Override
    public List<MetricsDto> getAllMetrics() {

        // Create a span. If there was a span present in this thread it will become
        // the `newSpan`'s parent.
//        Span newSpan = this.tracer.nextSpan().name("calculateTax");
//        // Start a span and put it in scope. Putting in scope means putting the span
//        // in thread local
//        // and, if configured, adjust the MDC to contain tracing information
//        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
//            log.info("Start span");
//            // ...
//            // You can tag a span - put a key value pair on it for better debugging
//            newSpan.tag("taxValue1", "taxValue2");
//            // ...
//            // You can log an event on a span - an event is an annotated timestamp
//            newSpan.event("taxCalculated");
//        }
//        finally {
//            log.info("End span");
//            // Once done remember to end the span. This will allow collecting
//            // the span to send it to a distributed tracing system e.g. Zipkin
//            newSpan.end();
//        }

        List<MetricsDto> metricsDtos = new ArrayList<>();

        for (String url : listUrlMetrics) {
            ResponseEntity<Metrics> result = restTemplateClientProvider.getHttpRequest(url,
                    new ParameterizedTypeReference<>() {},
                    null);
            metricsDtos.add(metricsMapper.toDto(result.getBody()));
        }

        log.debug("Request to get list of Metrics : {}", metricsDtos);
        return metricsDtos;
    }
}
