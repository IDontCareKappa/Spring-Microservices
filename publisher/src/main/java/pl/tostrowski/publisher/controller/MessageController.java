package pl.tostrowski.publisher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tostrowski.publisher.model.Notification;
import pl.tostrowski.publisher.service.NotificationService;

@RestController
public class MessageController {

    private final NotificationService notificationService;

    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification("Kurs", notification);
        return "Wysłano notyfikacje";
    }

    @GetMapping("/notification")
    public ResponseEntity<?> sendStudentNotification(@RequestParam Long id) {
        return notificationService.sendStudentNotification(id);
    }

}
