package com.example.idleman;

public class Data {
    private static String id = "idleMan";
    private static String name = "idleMan";
    private static String label = "idleMan";
    private static String place = "idleMan";
    private static String qq = "idleMan";
    private static String weChat = "idleMan";
    private static String telNumber = "idleMan";

    public static void setId(String id) {
        Data.id = id;
    }

    public static void setLabel(String label) {
        Data.label = label;
    }

    public static void setName(String name) {
        Data.name = name;
    }

    public static void setPlace(String place) {
        Data.place = place;
    }

    public static void setQq(String qq) {
        Data.qq = qq;
    }

    public static void setTelNumber(String telNumber) {
        Data.telNumber = telNumber;
    }

    public static void setWeChat(String weChat) {
        Data.weChat = weChat;
    }

    public static String getId() {
        return id;
    }

    public static String getLabel() {
        return label;
    }

    public static String getName() {
        return name;
    }

    public static String getPlace() {
        return place;
    }

    public static String getQq() {
        return qq;
    }

    public static String getTelNumber() {
        return telNumber;
    }

    public static String getWeChat() {
        return weChat;
    }
}
