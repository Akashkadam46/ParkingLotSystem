package com.infinite.java.parkinglot;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.datepicker.DateMetadataModel;

public class Slot {

	private int slotId;
	private String vehicleNumber;
	private Date startDate;
	private Date endDate;
	private int slotNumber;
	private SCategory sCategory;
	private int rId;
	private String bookedBy;
	private double totalBill;
	private Registration_Entity register;	

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	public String getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public SCategory[] getSCategories() {
		return SCategory.values();
	}

	public Registration_Entity getRegister() {
		return register;
	}

	public void setRegister(Registration_Entity register) {
		this.register = register;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SCategory getsCategory() {
		return sCategory;
	}

	public void setsCategory(SCategory sCategory) {
		this.sCategory = sCategory;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}
	
	private Date restrictedDate;
	
	
	public Date getRestrictedDate() {
		return restrictedDate;
	}

	public void setRestrictedDate(Date restrictedDate) {
		this.restrictedDate = restrictedDate;
	}
	
	
	private Date startDateRestriction=new Date();
	private Date endDateRestriction = new Date();
	
	
	

	public Date getEndDateRestriction() {
		return endDateRestriction;
	}

	public void setEndDateRestriction(Date endDateRestriction) {
		this.endDateRestriction = endDateRestriction;
	}

	public Date getStartDateRestriction() {
		return startDateRestriction;
	}

	public void setStartDateRestriction(Date startDateRestriction) {
		this.startDateRestriction = startDateRestriction;
	}
	
	

	public void startDateChanged(SelectEvent event) {
        // Perform actions when the start date is changed
        // You can restrict the end date here
        // For example, set a minimum end date based on the selected start date
        if (startDate != null) {
        	
            restrictedDate = DateUtils.addDays(startDate, 15);
            endDateRestriction=DateUtils.addDays(startDate,30);
        }
	}
	

	
//	public void startDateChanged(SelectEvent event) {
//	    if (startDate != null) {
//	        // Calculate the minimum end date (30 days after the selected start date)
//	        startDate = DateUtils.addDays(startDate, 15);
//	    }
//	}
//	
//
//
//	public void endDateChanged(SelectEvent event) {
//	    if (endDate != null) {
//	        // Calculate the minimum start date (15 days before the selected end date)
//	        endDate = DateUtils.addDays(endDate, 15);
//	    }
//	}


	@Override
	public String toString() {
		return "Slot [slotId=" + slotId + ", vehicleNumber=" + vehicleNumber + ", startDate=" + startDate + ", endDate="
				+ endDate + ", slotNumber=" + slotNumber + ", sCategory=" + sCategory + ", rId=" + rId + ", bookedBy="
				+ bookedBy + ", totalBill=" + totalBill + ", register=" + register + "]";
	}
	
	
	

}
