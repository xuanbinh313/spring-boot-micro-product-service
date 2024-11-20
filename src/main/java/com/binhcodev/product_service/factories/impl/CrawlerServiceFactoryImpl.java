package com.binhcodev.product_service.factories.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.binhcodev.product_service.enums.CrawType;
import com.binhcodev.product_service.factories.CrawlerServiceFactory;
import com.binhcodev.product_service.services.CrawlerService;
import com.binhcodev.product_service.services.impl.CellPhoneSCrawlerServiceImpl;
import com.binhcodev.product_service.services.impl.DMXCrawlerServiceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CrawlerServiceFactoryImpl implements CrawlerServiceFactory {
    private final CellPhoneSCrawlerServiceImpl cellPhoneSCrawlerServiceImpl;
    private final DMXCrawlerServiceImpl dmxCrawlerServiceImpl; 
    private final Map<CrawType, CrawlerService> crawlerServices;

    @PostConstruct
    private void init(){
        register(CrawType.CellPhoneS, cellPhoneSCrawlerServiceImpl);
        register(CrawType.DMX, dmxCrawlerServiceImpl);
    }

    private void register(CrawType type, CrawlerService service) {
        crawlerServices.put(type, service);
    }

    public CrawlerService getCrawlerService(CrawType crawlerKey) {
        CrawlerService service = crawlerServices.get(crawlerKey);
        if (service == null) {
            throw new IllegalArgumentException("No CrawlerService found for key: " + crawlerKey);
        }
        return service;
    }
}
