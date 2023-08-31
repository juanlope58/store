package com.juanlopera.store.dto;

import java.util.List;

public class SaleRequestDTO {

    private Long customerId;
    private List<Long> productIds;
    
    public SaleRequestDTO() {
        
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

}
