package model;

public class Cake {
    private int cakeId;
    private String cakeName;
    private int price;

    public Cake(int cakeId, String cakeName, int price) {
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.price = price;
    }

    public String toString() {
        return "[" + cakeId + "] " + cakeName + " - " + price + "원";
    }
}
