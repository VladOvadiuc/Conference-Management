package ro.ubb.catalog.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class SessionDto extends BaseDto {
    private String location;
    private LocalDateTime dateTime;
}
