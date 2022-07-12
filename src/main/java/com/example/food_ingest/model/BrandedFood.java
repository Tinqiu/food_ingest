package com.example.food_ingest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandedFood {
    private String brandOwner;
    private String brandName;
    private String ingredients;
    private Double servingSize;
    private String servingSizeUnit;

    public BrandedFood(String brandOwner, String brandName, String ingredients, String servingSize, String servingSizeUnit) {
        this.brandOwner = brandOwner.replace("\"","");
        this.brandName = brandName.replace("\"","");
        this.ingredients = ingredients.replace("\"","");
        this.servingSize = parseDoubleOrNull(servingSize.replace("\"",""));
        this.servingSizeUnit = servingSizeUnit.replace("\"","");
    }

    private Double parseDoubleOrNull(String value){
        try{
            return Double.valueOf(value);
        } catch (NumberFormatException e){
            return null;
        }
    }
}
