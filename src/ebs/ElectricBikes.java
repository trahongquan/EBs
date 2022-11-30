
package ebs;

import java.io.Serializable;

//class Test {
//    public static void main(String[] args) {
//    ElectricBikes eb = new ElectricBikes(141, "yamaha","spot", "red", 15000);
//        System.out.println(eb.toString());
//    }
//}

public class ElectricBikes implements Serializable{
        private int ID;
        private String Marker;
        private String Type;
        private String Color;
        private double Price;

    public ElectricBikes() {
    }

    public ElectricBikes(int ID, String Marker, String Type, String Color, double Price) {
        this.ID = ID;
        this.Marker = Marker;
        this.Type = Type;
        this.Color = Color;
        this.Price = Price;
    }

    @Override
    public String toString() {
        return "EBs{" + "ID=" + ID + ", Marker=" + Marker + ", Type=" + Type + ", Color=" + Color + ", Price=" + Price + '}';
    }
        
      
    
        
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMarker() {
        return Marker;
    }

    public void setMarker(String Marker) {
        this.Marker = Marker;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
//    ///////////////////////
        void getID(int parseInt) {
    }

    void getMarker(String text) {
    }

    void getType(String text) {
    }

    void getColor(String text) {
    }

    void getPrice(double parseDouble) {
    }
    
}
