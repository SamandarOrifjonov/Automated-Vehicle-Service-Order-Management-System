package uz.pdp.vehicle_service.service;

import org.springframework.stereotype.Service;

import uz.pdp.vehicle_service.entity.ServiceOrder;
import uz.pdp.vehicle_service.entity.User;

@Service
public class NotificationService {

    public void sendStatusChangeNotification(User user, ServiceOrder order) {
        // TODO: integrate real SMS/EMAIL notification here
        System.out.println("[NOTIFICATION] Notify " + user.getEmail() +
                " about order #" + order.getId() +
                " → new status: " + order.getStatus());
    }
}
