package com.binhcodev.product_service.factories;

import com.binhcodev.product_service.enums.CrawType;
import com.binhcodev.product_service.services.CrawlerService;

public interface CrawlerServiceFactory {

    public CrawlerService getCrawlerService(CrawType crawlerKey);
}
