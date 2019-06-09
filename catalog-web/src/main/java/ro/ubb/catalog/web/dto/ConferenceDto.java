package ro.ubb.catalog.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class ConferenceDto extends BaseDto {
    private String name;
    private String location;
    private String deadlineAbstract;

    private String deadlinePaper;
    private String startPaper;

    private String startDate;
    private String endDate;
}
