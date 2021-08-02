package sample.Entity;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Department {
    private int id;
    private final String name;
}
