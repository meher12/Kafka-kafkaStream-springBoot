package net.javaspring.kafkaconsumer.entity;

public class SimpleNumber {

    private int number;

    public SimpleNumber(){}


    public SimpleNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "SimpleNumber{" + "number=" + number + '}';
    }
}
