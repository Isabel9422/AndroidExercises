package com.example.serna_agudo_isabel_pmdm_tarea04.model;

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
    public
    int id;

    @ColumnInfo(name = "notification_type")
    String notifyType;

    @ColumnInfo(name = "message")
    String msg;

    @ColumnInfo(name = "phone_number")
    int phoneNumber;

    @ColumnInfo(name = "birthdate")
    String birthDate;

    @ColumnInfo(name = "name")
    String name;

    public Contact() {
    }

    public Contact(String notifyType, String msg, int phoneNumber, String birthDate, String name) {
        this.notifyType = notifyType;
        this.msg = msg;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.name = name;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = this.notifyType;
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
}