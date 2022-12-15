package com.thebeans.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class AccountSettings {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email(message = "It doesn't look like a valid e-mail address!")
    private String email;

    private String currentPassword;

    private String newPassword;

    private String newPasswordRepeat;

    private String motto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;

    public static AccountSettings fromUser(User user) {
        AccountSettings accountSettings = new AccountSettings();
        accountSettings.setFirstName(user.getFirst_name());
        accountSettings.setLastName(user.getLast_name());
        accountSettings.setEmail(user.getEmail());
        accountSettings.setMotto(user.getMotto());
        accountSettings.setBirthday(user.getBirthday());
        accountSettings.setPhone(user.getPhone());
        return accountSettings;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
