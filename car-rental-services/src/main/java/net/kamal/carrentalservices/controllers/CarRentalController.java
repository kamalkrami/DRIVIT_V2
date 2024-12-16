package net.kamal.carrentalservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.carrentalservices.client.CarsRestClient;
import net.kamal.carrentalservices.client.UsersRestClient;
import net.kamal.carrentalservices.entities.CarRental;
import net.kamal.carrentalservices.model.Cars;
import net.kamal.carrentalservices.model.Users;
import net.kamal.carrentalservices.repositories.CarRentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CarRentalController {

    CarRentalRepository carRentalRepository;
    CarsRestClient carsRestClient;
    UsersRestClient usersRestClient;

    @GetMapping("/carrental")
    public List<CarRental> getAllCarRental(){
        List<CarRental> carRentalList = carRentalRepository.findAll();
        carRentalList.forEach(carRental -> {
            carRental.setCars(carsRestClient.getCarsById(carRental.getId_car()));
            carRental.setUsers(usersRestClient.findUserById(carRental.getId_user()));
        });
        return carRentalList;
    }

    @GetMapping("/carrental/{id_user}")
    public CarRental getCarRental(@PathVariable Long id_user){
        CarRental carRental = carRentalRepository.findById(id_user).get();
        Cars cars = carsRestClient.getCarsById(carRental.getId_car());
        Users users = usersRestClient.findUserById(carRental.getId_user());
        carRental.setUsers(users);
        carRental.setCars(cars);
        return carRental;
    }

    @PostMapping("/carrental/addCarrental")
    public ResponseEntity<Map<String, Object>> addCarRental(@RequestBody CarRental carRental){
        if (carRental == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "msg", "CarRental cannot be null",
                "status", 400
        ));
        carRentalRepository.save(carRental);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "CarRental has been added successfully",
                "status", 201
        ));
    }


}
