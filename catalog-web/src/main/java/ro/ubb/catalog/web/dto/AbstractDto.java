package ro.ubb.catalog.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class AbstractDto extends BaseDto {
    private String abstractPath;
    private String title;
    private String keywords;
    private String topics;
    private String authorNames;
    private Long authorId;
    private String status;
}
