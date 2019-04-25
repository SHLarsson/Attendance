package com.example.simon.attendence;

public class User {

    String username;
    String usersClass;

    public User() {
        //Empty Constructor For Firebase
    }


    public User(String username, String password)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.usersClass = usersClass;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsersClass()
    {
        return usersClass;
    }
    public void setUsersClass(String password)
    {
        this.usersClass = usersClass;
    }
}