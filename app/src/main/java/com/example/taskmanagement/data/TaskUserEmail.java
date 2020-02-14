package com.example.taskmanagement.data;

import java.io.Serializable;

public class TaskUserEmail implements Serializable {

    public String email;

    public TaskUserEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
