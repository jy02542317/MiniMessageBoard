package learning.java.minimessageboard.Dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;
}
