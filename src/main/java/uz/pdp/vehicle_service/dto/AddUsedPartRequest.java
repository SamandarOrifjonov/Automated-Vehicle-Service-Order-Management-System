package uz.pdp.vehicle_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddUsedPartRequest {
    private Long orderId;
    private String partName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
