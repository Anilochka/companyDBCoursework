package sample.Entity;

import lombok.*;
import java.sql.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Project {
    private int id;
    private final String name;
    private final int cost;
    private final int departmentId;
    private final Date dateBeg;
    private final Date dateEnd;
    private final Date dateEndReal;
    private String profit = "0";
    private String departmentName = "";
}
