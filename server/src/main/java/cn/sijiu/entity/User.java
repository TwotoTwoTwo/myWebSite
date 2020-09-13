package cn.sijiu.entity;

import cn.sijiu.Utils;

public class User {
    String userId;
    String userName;
    String userPassword;
    String sex;
    int age;
    String declaration;

    public void filter(){
        userName= Utils.datafilter(userName);
        declaration= Utils.datafilter(declaration);
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getDeclaration() {
        return declaration;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
