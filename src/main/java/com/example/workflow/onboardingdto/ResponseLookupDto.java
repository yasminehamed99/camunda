package com.example.workflow.onboardingdto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLookupDto {
    private Long id;
    private String group;
    private String code;
    private Long parentLookupId;
    private List<ResponseLookupDto> childs;
    private Map<String, String> metaDataMap;
}
