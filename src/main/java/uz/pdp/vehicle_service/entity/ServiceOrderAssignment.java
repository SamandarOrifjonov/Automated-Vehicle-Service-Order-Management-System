package uz.pdp.vehicle_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_order_assignments")
@Getter
@Setter
public class ServiceOrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "service_order_id", unique = true)
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private User mechanic;

    private LocalDateTime assignedAt;
}
