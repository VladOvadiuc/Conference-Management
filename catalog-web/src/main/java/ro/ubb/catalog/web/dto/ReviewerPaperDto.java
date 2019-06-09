package ro.ubb.catalog.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class ReviewerPaperDto extends BaseDto{
    private Long reviewerId;
    private Long paperId;
    private String result;
}
