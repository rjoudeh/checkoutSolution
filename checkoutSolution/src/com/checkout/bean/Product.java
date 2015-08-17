package com.checkout.bean;

import java.math.BigDecimal;

/**
 * 
 * @author Rania
 * 
 */
public class Product {
	private String name;
	private BigDecimal unitPrice;
	private int noOfItems;
	private boolean hasOffer;

	/**
	 * 
	 * @param name
	 * @param unitPrice
	 */
	public Product(String name, BigDecimal unitPrice) {
		this.name = name;
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the noOfItems
	 */
	public int getNoOfItems() {
		return noOfItems;
	}

	/**
	 * @param noOfItems
	 *            the noOfItems to set
	 */
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	/**
	 * @return the hasOffer
	 */
	public boolean isHasOffer() {
		return hasOffer;
	}

	/**
	 * @param hasOffer
	 *            the hasOffer to set
	 */
	public void setHasOffer(boolean hasOffer) {
		this.hasOffer = hasOffer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
