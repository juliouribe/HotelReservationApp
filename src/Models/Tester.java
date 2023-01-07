package Models;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("Julio", "Uribe", "juribe@gmail.com");
        System.out.println(customer);

        try {
            Customer badCustomer = new Customer("first", "last", "isthisit");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
