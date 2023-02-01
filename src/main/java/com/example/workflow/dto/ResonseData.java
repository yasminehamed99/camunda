package com.example.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResonseData {
    public ArrayList<Link> links;
    public String id;
    public String definitionId;
    public Object businessKey;
    public Object caseInstanceId;
    public boolean ended;
    public boolean suspended;
    public Object tenantId;
}
