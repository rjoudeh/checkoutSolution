package com.checkout.bean;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Define products and their prices
 * 
 * @author Rania
 * 
 */
public enum ProductEnum {

	ORANGE("orange", new BigDecimal(0.25)), APPLE("apple", new BigDecimal(0.60));

	private String name;
	private BigDecimal price;

	static EnumSet<ProductEnum> allOf = EnumSet.allOf(ProductEnum.class);
	static Map<String, ProductEnum> productsMap = new HashMap<String, ProductEnum>();
	static {
		for (ProductEnum productEnum : allOf) {
			productsMap.put(productEnum.getName(), productEnum);
		}
	}

	private ProductEnum(String name, BigDecimal price) {
		this.setName(name);
		this.setPrice(price);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static ProductEnum getProductByName(String name) {
		if (name != null)
			name = name.toLowerCase();

		return productsMap.get(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean compareProducts(String name) {
		return name != null && this.name.equalsIgnoreCase(name);
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
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
