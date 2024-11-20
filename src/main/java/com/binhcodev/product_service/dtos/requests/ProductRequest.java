package com.binhcodev.product_service.dtos.requests;

import java.util.List;

import com.binhcodev.product_service.enums.CrawType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    String url;
    List<String> categories;
    CrawType type;
}
