package ro.ubb.catalog.web.dto;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class ReviewerAbstractDto extends BaseDto {
    private Long reviewerId;
    private Long abstractId;
    private String result;
}
