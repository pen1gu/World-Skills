package model;

import vehicle.Car;

public class AutoVehicle {

	public VehicleFunction createFunction(String type, String path) {
		VehicleFunction function = null;
		switch (type) {
		case "승용차": {
			function = new Car(path);
			break;
		}
//		case "승합차": {
//			function = new Van(path);
//			break;
//		}
//		case "화물차": {
//			function = new Truck(path);
//			break;
//		}
//		case "장애인차": {
//			function = new DisableCar(path);
//			break;
//		}
//		case "오토바이차": {
//			function = new AutoBicycle(path);
//			break;
//		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}

		return function;
	}
}
