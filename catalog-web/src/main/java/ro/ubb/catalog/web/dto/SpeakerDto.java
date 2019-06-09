package ro.ubb.catalog.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class SpeakerDto extends BaseDto {
    private String username;
    private String password;
    private String name;
    private String affiliation;
    private String email;
}
