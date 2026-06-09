package uz.pdp.vehicle_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOrderRequest {
    private Long customerId;
    private Long vehicleId;
    private Long serviceTypeId;
    private LocalDate scheduledDate;
    private String description;
}
