package org.example.nuiifo0d.utils;

public class HideData {
    public static String hideEmail(String email) {
        if (email == null) {
            return null;
        }

        String[] emails = email.split("@");
        if (emails.length != 2) {
            return email;
        }

        String twoChar = emails[0].substring(0, 2);
        String body = twoChar + emails[0].substring(2).replaceAll(".", "*");
        return body + "@" + emails[1];
    }

    public static String hidePhone(String phone) {
        if (phone == null) {
            return null;
        }

        String lastTwo = phone.substring(phone.length() - 2);
        return "*".repeat(phone.length() - 2) + lastTwo;
    }
}
