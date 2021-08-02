package sample.Entity;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class DepartmentEmployee {
    private int id;
    private final int departmentId;
    private final int employeeId;
    private String departmentName = "";
    private String employeeName = "";
}
