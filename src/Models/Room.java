package Models;

public class Room implements IRoom {
    public String roomNumber;
    public Double price;
    public RoomType enumeration;
    public boolean isFree;
    public boolean isPineappleRoom;

    public Room (String roomNumber, Double price, RoomType enumeration, boolean isFree, boolean isPineappleRoom) {
        super();
        this.roomNumber  = roomNumber;
        this.price  = price;
        this.enumeration  = enumeration;
        this.isFree = isFree;
        this.isPineappleRoom = isPineappleRoom;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }
    @Override
    public Double getRoomPrice() {
        return price;
    }
    @Override
    public RoomType getRoomType() {
        return enumeration;
    }
    @Override
    public boolean isFree() {
        return isFree;
    }
    @Override
    public boolean isPineappleRoom() { return isPineappleRoom; }

    @Override
    public String toString() {
        if (isPineappleRoom) {
            String msg = "Room number " + roomNumber + ", Price " + price + ", Room type " + enumeration + ". "
                    + "The Pineapple Suite comes with your own plunge pool and private patio. ";
            return msg;
        }
        return "Room number " + roomNumber + ", Price " + price + ", Room type " + enumeration;
    }
}
