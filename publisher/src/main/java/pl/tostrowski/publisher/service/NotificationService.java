package pl.tostrowski.publisher.service;

import org.springframework.http.ResponseEntity;
import pl.tostrowski.publisher.model.Notification;
import pl.tostrowski.publisher.model.Student;

public interface NotificationService {

    public void sendNotification(String routingKey, Notification notification);
    public ResponseEntity<Student> sendStudentNotification(Long studentId);

}
