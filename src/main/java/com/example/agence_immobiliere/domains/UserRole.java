package com.example.agence_immobiliere.domains;

public enum UserRole {
    ADMIN, USER;

    @Override
    public String toString(){
        return this == UserRole.ADMIN ? "ADMIN" : this == UserRole.USER ? "USER" : "UNKNOWN";
    }
}
