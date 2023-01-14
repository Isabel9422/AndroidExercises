package com.example.serna_agudo_isabel_pmdm_tarea04;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "miscumples")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "notification_type")
    String notifType;

    @ColumnInfo(name = "message")
    String msg;

    @ColumnInfo(name = "phone_number")
    int phoneNumber;

    @ColumnInfo(name = "birthdate")
    Date birthDate;

    @ColumnInfo(name = "name")
    String name;

    public Contact() {
    }

    public Contact(String notifType, String msg, int phoneNumber, Date birthDate, String name) {
        this.notifType = notifType;
        this.msg = msg;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.name = name;
    }

    public String getNotifType() {
        return notifType;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}