package com.bridgelabz.cabinvoicegenerator;

import java.util.*;

public class CabInvoiceGenerator {

	private static final double NORMAL_COST_PER_KM = 10.0;
	private static final double NORMAL_COST_PER_MINUTE = 1.0;
	private static final double NORMAL_MIN_FARE = 5.0;
	private static final double PREMIUM_COST_PER_KM = 15.0;
	private static final double PREMIUM_COST_PER_MINUTE = 2.0;
	private static final double PREMIUM_MIN_FARE = 20.0;

	public static void main(String[] args) {
		Map<Integer, List<Ride>> rideRepository = new HashMap<>();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter User ID (or type 'done' to finish): ");
			String input = scanner.nextLine();

			if (input.equalsIgnoreCase("done")) {
				break;
			}

			int userId = Integer.parseInt(input);

			System.out.println("Enter Ride Details (type 'done' to finish).");

			List<Ride> rides = new ArrayList<>();

			while (true) {
				String rideInput = scanner.nextLine();

				if (rideInput.equalsIgnoreCase("done")) {
					break;
				}

				String[] rideData = rideInput.split(" ");

				double distance = Double.parseDouble(rideData[0]);
				int time = Integer.parseInt(rideData[1]);
				boolean isPremium = Boolean.parseBoolean(rideData[2]);

				Ride ride = new Ride(distance, time, isPremium);
				rides.add(ride);
			}

			rideRepository.put(userId, rides);
		}

		for (Map.Entry<Integer, List<Ride>> entry : rideRepository.entrySet()) {
			int userId = entry.getKey();
			List<Ride> rides = entry.getValue();

			Invoice invoice = generateInvoice(rides);

			System.out.println("User ID: " + userId);
			System.out.println("Total Rides: " + invoice.getTotalRides());
			System.out.println("Total Fare: " + invoice.getTotalFare());
			System.out.println("Average Fare Per Ride: " + invoice.getAverageFarePerRide());
			System.out.println();
		}
	}

	private static Invoice generateInvoice(List<Ride> rides) {
		double totalFare = 0;
		int totalRides = rides.size();

		for (Ride ride : rides) {
			double distance = ride.getDistance();
			int time = ride.getTime();
			boolean isPremium = ride.isPremium();

			double costPerKm, costPerMinute, minFare;

			if (isPremium) {
				costPerKm = PREMIUM_COST_PER_KM;
				costPerMinute = PREMIUM_COST_PER_MINUTE;
				minFare = PREMIUM_MIN_FARE;
			} else {
				costPerKm = NORMAL_COST_PER_KM;
				costPerMinute = NORMAL_COST_PER_MINUTE;
				minFare = NORMAL_MIN_FARE;
			}

			double fare = costPerKm * distance + costPerMinute * time;
			fare = Math.max(fare, minFare);

			totalFare += fare;
		}

		double averageFarePerRide = totalFare / totalRides;

		return new Invoice(totalRides, totalFare, averageFarePerRide);
	}

	private static class Ride {
		private double distance;
		private int time;
		private boolean isPremium;

		public Ride(double distance, int time, boolean isPremium) {
			this.distance = distance;
			this.time = time;
			this.isPremium = isPremium;
		}

		public double getDistance() {
			return distance;
		}

		public int getTime() {
			return time;
		}

		public boolean isPremium() {
			return isPremium;
		}
	}

	private static class Invoice {
		private int totalRides;
		private double totalFare;
		private double averageFarePerRide;

		public Invoice(int totalRides, double totalFare, double averageFarePerRide) {
			this.totalRides = totalRides;
			this.totalFare = totalFare;
			this.averageFarePerRide = averageFarePerRide;
		}

		public int getTotalRides() {
			return totalRides;
		}

		public double getTotalFare() {
			return totalFare;
		}

		public double getAverageFarePerRide() {
			return averageFarePerRide;
		}
	}
}
