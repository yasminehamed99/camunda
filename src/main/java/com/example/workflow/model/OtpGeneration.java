package com.example.workflow.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class OtpGeneration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String otp;


    private Date otpCreationDate;
    @Column(name = "vat", columnDefinition = "VARCHAR(15)")
    private String vatNumber;
@Column(name = "status")
    private boolean status;



}