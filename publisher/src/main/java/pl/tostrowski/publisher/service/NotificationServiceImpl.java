package pl.tostrowski.publisher.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.tostrowski.publisher.model.Notification;
import pl.tostrowski.publisher.model.Student;

import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;
    private static final String URL_STUDENT_SERVICE = "http://localhost:8080/students/";

    public NotificationServiceImpl(RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotification(String routingKey, Notification notification)
    {
        rabbitTemplate.convertAndSend(routingKey, notification);
    }

    public Student getStudentFromService(Long studentId){
        return restTemplate.exchange(URL_STUDENT_SERVICE + studentId,
                HttpMethod.GET, HttpEntity.EMPTY, Student.class).getBody();
    }

    public Notification makeNotification(Student student){
        Notification notification = new Notification();
        notification.setEmail(student.getEmail());
        notification.setTitle("Witaj " + student.getFirstName() + "!");
        notification.setBody("Miło, że jesteś z nami " + student.getFirstName() + " " + student.getLastName());
        return notification;
    }

    @Override
    public ResponseEntity<Student> sendStudentNotification(Long studentId){
        Student student = getStudentFromService(studentId);
        if (Objects.equals(student, ResponseEntity.ok())){
            Notification notification = makeNotification(student);
            sendNotification("Kurs", notification);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
