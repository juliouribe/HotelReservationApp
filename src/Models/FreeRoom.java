package Models;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, price, enumeration);
        this.price = 0.0;
        this.isFree = true;
    }
    @Override
    public String toString() {
        return "Room number " + roomNumber + ", This room is free, Room type " + enumeration;
    }
}
