package com.sportevents.participants;

import java.sql.Date;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParticipantRequest {
    private String idCard;
    private String birthDate;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String gender;
    private String street;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;
    private String emergencyPhoneNumber;
    private String email;


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return !(idCard.isEmpty() || birthDate.isEmpty() || name.isEmpty() || firstSurname.isEmpty() || secondSurname.isEmpty()
                || gender.isEmpty() || street.isEmpty() || zipCode.isEmpty() || city.isEmpty()
                || phoneNumber.isEmpty() || email.isEmpty());
    }

    public Participant generateParticipant() {
        Participant participant = new Participant();

        participant.setIdCard(this.getIdCard());
        participant.setBirthDate(Date.valueOf(this.getBirthDate()));
        participant.setName(this.getName());
        participant.setFirstSurname(this.getFirstSurname());
        participant.setSecondSurname(this.getSecondSurname());
        participant.setGender(this.getGender());
        participant.setStreet(this.getStreet());
        participant.setZipCode(this.getZipCode());
        participant.setCity(this.getCity());
        participant.setState(this.getState());
        participant.setCountry(this.getCountry());
        participant.setPhoneNumber(this.getPhoneNumber());
        participant.setEmergencyPhoneNumber(this.getEmergencyPhoneNumber());
        participant.setEmail(this.getEmail());

        return participant;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String participantRequestAsJson = mapper.writeValueAsString(this);

            return participantRequestAsJson;
        } catch (JsonProcessingException jpe) {
            return jpe.getMessage();
        }
    }

}
