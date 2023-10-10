package com.example.geocare.Model;

import android.content.Context;

import com.example.geocare.Schedule.ProductItem;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    String UserName;

    String UserEmail;
    String UserPassword;
    String UserAge;
    String UserSkinType;
    ArrayList<String> UserSkinCondition;
    ArrayList<String> UserSelf;
    ArrayList<String>UserFavorite;
    ArrayList<ProductItem> UserProductList = new ArrayList<>();
    String UserCurrentDay; // The day of the last routine
    int UserDayOrNight = 0; // 0 for Day and 1 for Night
    int UserNumberDone = 0;
    String ava;

    public User() {
    }

    public User(String userName, String userEmail, String userPassword, String userAge, String userSkinType, ArrayList<String> userSkinCondition, ArrayList<String> userSelf, ArrayList<String> userFavorite, ArrayList<ProductItem> userProductList, String userCurrentDay, int userDayOrNight, int userNumberDone, String ava) {
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserAge = userAge;
        UserSkinType = userSkinType;
        UserSkinCondition = userSkinCondition;
        UserSelf = userSelf;
        UserFavorite = userFavorite;
        UserProductList = userProductList;
        UserCurrentDay = userCurrentDay;
        UserDayOrNight = userDayOrNight;
        UserNumberDone = userNumberDone;
        this.ava = ava;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", UserEmail='" + UserEmail + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", UserAge='" + UserAge + '\'' +
                ", UserSkinType='" + UserSkinType + '\'' +
                ", UserSkinCondition=" + UserSkinCondition +
                ", UserSelf=" + UserSelf +
                ", UserFavorite=" + UserFavorite +
                ", UserCurrentDay='" + UserCurrentDay + '\'' +
                ", UserDayOrNight='" + UserDayOrNight + '\'' +
                ", UserNumberDone='" + UserNumberDone + '\'' +
                ", ava='" + ava + '\'' +
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

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserSkinType() {
        return UserSkinType;
    }

    public void setUserSkinType(String userSkinType) {
        UserSkinType = userSkinType;
    }

    public ArrayList<ProductItem> getUserProductList() {
        return UserProductList;
    }

    public void setUserProductList(ArrayList<ProductItem> userProductList) {
        UserProductList = userProductList;
    }

    public String getUserCurrentDay() {
        return UserCurrentDay;
    }

    public int getUserDayOrNight() {
        return UserDayOrNight;
    }

    public void setUserCurrentDay(String userCurrentDay) {
        UserCurrentDay = userCurrentDay;
    }

    public void setUserDayOrNight(int userDayOrNight) {
        UserDayOrNight = userDayOrNight;
    }

    public ArrayList<String> getUserSkinCondition() {
        return UserSkinCondition;
    }

    public void setUserSkinCondiction(ArrayList<String> userSkinCondiction) {
        UserSkinCondition = userSkinCondiction;
    }

    public int getUserNumberDone() {
        return UserNumberDone;
    }

    public void setUserNumberDone(int userNumberDone) {
        UserNumberDone = userNumberDone;
    }

    public int getImageDetailResourceIdByInt(Context context)
    {
        int id = context.getResources().getIdentifier(this.ava, "drawable", context.getPackageName());
        return id;
    }

    public boolean checkFavoriteProduct(String name)
    {
        if(this.UserFavorite.contains(name))
        {
            return true;
        }
        else
            return false;
    }

    public void addToUserFavorite(String name)
    {
       if(this.UserFavorite==null)
       {
           this.UserFavorite=new ArrayList<>();

       }
       this.UserFavorite.add(name);
    }
    public  void removeUserFavorite(String name)
    {
        this.UserFavorite.remove(name);
    }

    public void setUserSkinCondition(ArrayList<String> userSkinCondition) {
        UserSkinCondition = userSkinCondition;
    }

    public ArrayList<String> getUserSelf() {
        return UserSelf;
    }

    public void setUserSelf(ArrayList<String> userSelf) {
        UserSelf = userSelf;
    }

    public ArrayList<String> getUserFavorite() {

        if(this.UserFavorite==null)
        {
            this.UserFavorite=new ArrayList<>();
        }
        return UserFavorite;
    }

    public void setUserFavorite(ArrayList<String> userFavorite) {

        UserFavorite = userFavorite;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public void addToUserCondition(String data)
    {
        if(this.UserFavorite==null)
        {
            this.UserFavorite=new ArrayList<>();
        }
        this.UserFavorite.add(data);
    }
    public void removeToUserCondition(String data)
    {
        if(this.UserSkinCondition==null)
        {
            this.UserSkinCondition=new ArrayList<>();
        }
        this.UserSkinCondition.remove(data);
    }
    public  String conditionListToString() {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.UserSkinCondition.size(); i++) {
            result.append(this.UserSkinCondition.get(i));


            if (i < this.UserSkinCondition.size() - 1) {
                result.append(",");
            }
        }

        return result.toString();
    }

}

