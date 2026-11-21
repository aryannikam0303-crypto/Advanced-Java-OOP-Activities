// Program 3: Runtime Polymorphism using Dynamic Method Dispatch
class Notification {
    void send() {
        System.out.println("Sending a general notification.");
    }
}

class EmailNotification extends Notification {
    @Override
    void send() {
        System.out.println("Sending notification through Email.");
    }
}

class SMSNotification extends Notification {
    @Override
    void send() {
        System.out.println("Sending notification through SMS.");
    }
}

class PushNotification extends Notification {
    @Override
    void send() {
        System.out.println("Sending Push notification.");
    }
}

public class Program03_RuntimePolymorphism {
    public static void main(String[] args) {
        Notification notification;

        notification = new EmailNotification();
        notification.send();

        notification = new SMSNotification();
        notification.send();

        notification = new PushNotification();
        notification.send();
    }
}
