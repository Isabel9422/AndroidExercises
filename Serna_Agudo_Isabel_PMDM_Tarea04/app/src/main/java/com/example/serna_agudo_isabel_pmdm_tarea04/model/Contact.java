package com.example.serna_agudo_isabel_pmdm_tarea04.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "miscumples")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "contact_id")
    int id;

    @ColumnInfo(name = "contact_notification_type")
    String notifyType;

    @ColumnInfo(name = "contact_message")
    String msg;

    @ColumnInfo(name = "contact_phone_number")
    String phoneNumber;

    @ColumnInfo(name = "contact_birthdate")
    String birthDate;

    @ColumnInfo(name = "contact_name")
    String name;


    public Contact(String notifyType, String msg, String phoneNumber, String birthDate, String name) {
        this.notifyType = notifyType;
        this.msg = msg;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.name = name;
    }

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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