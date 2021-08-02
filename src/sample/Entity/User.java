package sample.Entity;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
}
