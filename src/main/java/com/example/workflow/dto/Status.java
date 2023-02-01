package com.example.workflow.dto;

public enum Status {
    GOOD("Valid"), UNKNOWN("Unknown"), REVOKED("Revoked"), EXPIRED("Expired");
    private String name;

    Status(String name) {
        this.name = name;
    }

    public static Status getRevocationStatusByName(String name) {
        for(Status revocationStatus : Status.values()) {
            if(revocationStatus.getName().equals(name)) {
                return revocationStatus;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
