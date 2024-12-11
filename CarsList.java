import java.util.ArrayList;
import java.util.List;

public class CarsList {
    private List<Car> availableCars;

    public CarsList() {
        this.availableCars = new ArrayList<>();
        loadSampleCars();
    }

    private void loadSampleCars() {
        availableCars.add(new Car("Honda Civic RX 2024", 5, "Lahore", 20000,"civic.jpg", false));
        availableCars.add(new Car( "Toyota Corolla Altis", 5, "Islamabad", 17000,"corolla.png", false));
        availableCars.add(new Car( "Suzuki Swift 2023", 4, "Multan", 25000,"swift.png", true));
        availableCars.add(new Car( "Changan Alsvin", 5, "Sheikhupura", 15000,"alsvin.png", false));
    }

    public List<Car> getAvailableCars() {
        return availableCars;
    }
}