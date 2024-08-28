package com.infinite.java.parkinglot;

public class SlotAvailability {

	private int slotNumber;
	private int availability;
	private String reservedFor;

	public SlotAvailability() {
		super();

	}

	public SlotAvailability(int slotNumber, int availability, String reservedFor) {
		super();
		this.slotNumber = slotNumber;
		this.availability = availability;
		this.reservedFor = reservedFor;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public String getReservedFor() {
		return reservedFor;
	}

	public void setReservedFor(String reservedFor) {
		this.reservedFor = reservedFor;
	}

}
