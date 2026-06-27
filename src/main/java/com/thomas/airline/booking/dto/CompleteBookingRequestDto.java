package com.thomas.airline.booking.dto;

import com.thomas.airline.common.enums.Gender;
import com.thomas.airline.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CompleteBookingRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long flightId;
    @NotNull
    private Long flightSeatId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Integer age;
    @NotNull
    private Gender gender;
    @NotBlank
    private String passportNumber;
    @Positive
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentMethod paymentMethod;

    public CompleteBookingRequestDto() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getFlightSeatId() {
        return flightSeatId;
    }

    public void setFlightSeatId(Long flightSeatId) {
        this.flightSeatId = flightSeatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
