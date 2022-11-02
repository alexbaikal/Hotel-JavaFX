package com.example.prueba.models;

public class RoomModel {

    private int roomId;
    private int bookingId;
    private int roomNum;
    private int floor;
    private String availability;
    private String type;
    private String price;
    private String characteristics;





    public RoomModel(int roomId, int roomNum, int floor, String availability, String type, String price, String characteristics) {
        super();
        this.roomId = roomId;
        this.roomNum = roomNum;
        this.floor = floor;
        this.availability = availability;
        this.type = type;
        this.price = price;
        this.characteristics = characteristics;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }
}


