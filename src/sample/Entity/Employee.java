package sample.Entity;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String fatherName;
    private final String position;
    private final int salary;
}
