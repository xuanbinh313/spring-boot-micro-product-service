package com.binhcodev.product_service.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.binhcodev.product_service.entities.Variation;
import com.binhcodev.product_service.entities.VariationOption;
import com.binhcodev.product_service.repositories.VariationOptionRepository;
import com.binhcodev.product_service.repositories.VariationRepository;
import com.binhcodev.product_service.services.VariationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {
    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepository variationRepository;

    public List<VariationOption> createAllVariationOptions(List<String> names, Variation variation) {
        List<VariationOption> variationOptions = new ArrayList<>();
        for (String name : names) {
            VariationOption variationOption = variationOptionRepository.findVariationOptionByValue(name)
                    .orElseGet(() -> variationOptionRepository
                            .save(VariationOption
                                    .builder()
                                    .value(name)
                                    .variation(variation)
                                    .build()));
            variationOptions.add(variationOption);
        }
        return variationOptions;
    }

    public VariationOption createAutoVariationOption(String name, Variation variation) {
        return variationOptionRepository.findVariationOptionByValue(name)
                .orElseGet(() -> variationOptionRepository
                        .save(VariationOption
                                .builder()
                                .value(name)
                                .variation(variation)
                                .build()));
    }

    public Variation createAutoVariationByName(String name) {
        return variationRepository.findVariationByName(name).orElseGet(() -> {
            Variation newVariation = variationRepository.save(Variation.builder().name(name).build());
            return newVariation;
        });
    }
}
