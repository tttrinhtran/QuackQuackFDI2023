package com.example.geocare.Model;

import java.util.ArrayList;

public class User {

    String UserName;
    String UserEmail;
    String UserPassword;

    int UserAge;

    String UserSkinType;

    ArrayList<String> UserSkinCondition;

    public User() {
    }

    public User(String userName, String userEmail, String userPassword, int userAge, String userSkinType, ArrayList<String> userSkinCondition) {
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserAge = userAge;
        UserSkinType = userSkinType;
        UserSkinCondition = userSkinCondition;
    }
    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", UserEmail='" + UserEmail + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", UserAge=" + UserAge +
                ", UserSkinType='" + UserSkinType + '\'' +
                ", UserSkinCondition=" + UserSkinCondition +
                '}';
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public int getUserAge() {
        return UserAge;
    }

    public void setUserAge(int userAge) {
        UserAge = userAge;
    }

    public String getUserSkinType() {
        return UserSkinType;
    }

    public void setUserSkinType(String userSkinType) {
        UserSkinType = userSkinType;
    }

    public ArrayList<String> getUserSkinCondition() {
        return UserSkinCondition;
    }

    public void setUserSkinCondiction(ArrayList<String> userSkinCondiction) {
        UserSkinCondition = userSkinCondiction;
    }


}
