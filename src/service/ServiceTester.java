package service;

import Models.IRoom;

public class ServiceTester {
    public static void main(String[] args) {
        System.out.println(ReservationService.roomList.size());
        for (IRoom room : ReservationService.roomList) {
            System.out.println(room);
        }
    }
}
