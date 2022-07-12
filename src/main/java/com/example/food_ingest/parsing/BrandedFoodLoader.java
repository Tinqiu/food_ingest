package com.example.food_ingest.parsing;

import com.example.food_ingest.model.BrandedFood;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Component
public class BrandedFoodLoader {

    @Value("${brandedfood.filename}")
    private String brandedFoodFileName;

    public Stream<BrandedFood> loadFile() throws IOException {
        var resourceStream = getResourceStream();
        return resourceStream.map(row -> row.split("\","))
                .map(rowData -> new BrandedFood(rowData[1], rowData[2], rowData[5], rowData[7], rowData[8]));
    }

    private Stream<String> getResourceStream() throws IOException {
        var resource = getResource(brandedFoodFileName);
        var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        return reader.lines().onClose(() -> {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Resource getResource(String name) {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        return pathResolver.getResource(name);
    }
}
