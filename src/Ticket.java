public class Ticket {
    int row;
    int seat;
    double price;
    Person person;

    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public void  print(){
        System.out.println("Name : "+person.name);
        System.out.println("Surname : "+person.surname);
        System.out.println("Row number : "+(row+1)+" || "+"Seat number : "+(seat+1));
        System.out.println("Price : £"+ price);
    }


    public double getPrice(){
        return price;
    }
}
