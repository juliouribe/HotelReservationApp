package Models;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration, Boolean isFree, Boolean isPineappleRoom) {
        super(roomNumber, price, enumeration, isFree, isPineappleRoom);
        this.price = 0.0;
        this.isFree = true;
        this.isPineappleRoom = false;
    }
    @Override
    public String toString() {
        return "Room number " + roomNumber + ", This room is free, Room type " + enumeration;
    }
}
