package ShopAppBackend.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "logs")
@Data
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String message;

    public Log(String date, String message) {
        this.date = date;
        this.message = message;
    }

    public Log() {
    }
}
