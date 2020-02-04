package com.knu.ynortman.readerwritertask;

public class Data {
    private String name;
    private long phone;

    public Data(String name, long phone) {
        this.name = name;
        this.phone = phone;
    }

    public Data(String data) {
        String[] split = data.split(" ");
        this.name = split[0];
        this.phone = Long.parseLong(split[1]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
