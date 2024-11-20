package com.binhcodev.product_service.services;

import java.util.List;

import com.binhcodev.product_service.entities.Variation;
import com.binhcodev.product_service.entities.VariationOption;

public interface VariationService {

    public List<VariationOption> createAllVariationOptions(List<String> names, Variation variation);

    public VariationOption createAutoVariationOption(String name, Variation variation);

    public Variation createAutoVariationByName(String name);
}
