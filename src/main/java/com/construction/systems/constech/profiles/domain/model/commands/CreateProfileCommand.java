package com.construction.systems.constech.profiles.domain.model.commands;

public record CreateProfileCommand(String firstName, String lastName, String email, String street, String number, String city, String state, String zipCode) {
}