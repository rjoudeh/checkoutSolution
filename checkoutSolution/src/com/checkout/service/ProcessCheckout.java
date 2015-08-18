package com.checkout.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.checkout.bean.Product;
import com.checkout.bean.ProductEnum;
import com.checkout.errorHandling.CheckoutException;
import com.checkout.errorHandling.UndefinedProductException;
import com.checkout.helper.ConfigHelper;
import static com.checkout.bean.ProductEnum.*;

/**
 * Process checkout methods to calculate totals
 * 
 * @author Rania
 * 
 */
public class ProcessCheckout {

	/**
	 * Get total checkout of products
	 * 
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal calculateTotal(List<Product> productList)
			throws CheckoutException {
		BigDecimal total = new BigDecimal(0);
		// apply offers if enabled
		if (ConfigHelper.getInstance().isEnableOffers()) {
			total = calculateTotalAndApplyOffers(productList);
		} else {
			for (Product product : productList) {
				BigDecimal price = product.getUnitPrice().multiply(
						new BigDecimal(product.getNoOfItems()));

				total = total.add(price);
			}
		}

		return total;
	}

	/**
	 * Build list of product enum from arrays of strings
	 * 
	 * @param itemsArray
	 * @return
	 * @throws UndefinedProductException
	 */
	public static List<Product> buildListOfProducts(String[] itemsArray)
			throws UndefinedProductException {

		List<Product> list = new ArrayList<Product>();
		int apples = 0;
		int oranges = 0;
		for (String item : itemsArray) {
			ProductEnum productEnum = ProductEnum.getProductByName(item);

			if (productEnum == null) {
				throw new UndefinedProductException("Undefined product: "
						+ item);
			}

			switch (productEnum) {
			case APPLE:
				apples++;
				break;
			case ORANGE:
				oranges++;
				break;
			default:
				break;
			}
		}

		if (apples > 0) {
			Product appleProduct = new Product(APPLE.getName(),
					APPLE.getPrice());
			appleProduct.setNoOfItems(apples);
			appleProduct.setHasOffer(ConfigHelper.getInstance()
					.isEnableApplesOffer());
			list.add(appleProduct);
		}
		if (oranges > 0) {
			Product orangeProduct = new Product(ORANGE.getName(),
					ORANGE.getPrice());
			orangeProduct.setNoOfItems(oranges);
			orangeProduct.setHasOffer(ConfigHelper.getInstance()
					.isEnableOrangesOffer());
			list.add(orangeProduct);
		}

		return list;
	}

	/**
	 * Get formatted amount
	 * 
	 * @param amount
	 * @return
	 */
	public static String getFormattedTotal(BigDecimal amount) {
		return NumberFormat.getCurrencyInstance().format(amount);
	}

	/**
	 * Apply the offers and calculate total
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private static BigDecimal calculateTotalAndApplyOffers(List<Product> list)
			throws CheckoutException {

		BigDecimal total = new BigDecimal(0.0);

		for (Product product : list) {

			ProductEnum productEnum = ProductEnum.getProductByName(product
					.getName());

			if (productEnum == null) {
				throw new CheckoutException(
						"Error occured while calculating the total");
			}

			switch (productEnum) {
			case APPLE:
				// check if the apples product has enabled offer to apply!
				if (product.isHasOffer()) {
					BigDecimal calculateApplesOffer = calculateApplesOffer(
							product.getNoOfItems(), product.getUnitPrice());
					total = total.add(calculateApplesOffer);
				} else {
					// get the total of the apples normally!
					List<Product> applesList = new ArrayList<Product>();
					applesList.add(product);
					total = total.add(calculateTotal(applesList));
				}
				break;
			case ORANGE:
				// check if the oranges product has enabled offer to apply!
				if (product.isHasOffer()) {
					BigDecimal calculateOrangesOffer = calculateOrangesOffer(
							product.getNoOfItems(), product.getUnitPrice());
					total = total.add(calculateOrangesOffer);
				} else {
					// get the total checkout of the oranges normally!
					List<Product> orangesList = new ArrayList<Product>();
					orangesList.add(product);
					total = total.add(calculateTotal(orangesList));
				}
				break;

			default:
				break;
			}
		}

		return total;
	}

	/**
	 * Function that implements the offer (Buy one, get one free on Apples)
	 * 
	 * @example if the user buys 10 apples, he will pay for 5. if buys 5, he
	 *          will pay for 3
	 * @param list
	 * @return
	 */
	private static BigDecimal calculateApplesOffer(int noOfApples,
			BigDecimal applePrice) {
		int calculatedUnits = (int) Math.ceil((double) noOfApples / 2);
		BigDecimal total = applePrice.multiply(new BigDecimal(calculatedUnits));
		return total;
	}

	/**
	 * Function that implements the offer (Buy 3 for the price of 2 on Oranges)
	 * 
	 * @example if the user buys 6 oranges, he will pay for 4, if 10 oranges, he
	 *          will pay for 7
	 * @param list
	 * @return
	 */
	private static BigDecimal calculateOrangesOffer(int noOfOranges,
			BigDecimal orangePrice) {
		int includedInOfferUnits = (int) Math.floor(noOfOranges / 3);
		BigDecimal includedInOfferTotal = new BigDecimal(includedInOfferUnits)
				.multiply(new BigDecimal(2)).multiply(orangePrice);

		int notIncludedInOfferUnits = noOfOranges - (includedInOfferUnits * 3);
		BigDecimal notIncludedUnitsTotal = new BigDecimal(
				notIncludedInOfferUnits).multiply(orangePrice);

		BigDecimal total = includedInOfferTotal.add(notIncludedUnitsTotal);
		return total;
	}

	/**
	 * Print cart items!
	 * 
	 * @param itemsArray
	 * @return
	 * @throws UndefinedProductException
	 */
	public static String printCartItems(List<Product> products) {
		String cart = "";
		StringBuilder builder = new StringBuilder();

		for (Product product : products) {
			int count = product.getNoOfItems();
			if (APPLE.compareProducts(product.getName()))
				builder.append("[Apples: ").append(count).append("]");
			else if (ORANGE.compareProducts(product.getName()))
				builder.append("[Oranges: ").append(count).append("]");
		}

		cart = builder.toString();
		return cart;
	}

	/**
	 * Print applied offers!
	 * 
	 * @return
	 */
	public static String printAppliedOffers(List<Product> products) {
		StringBuilder builder = new StringBuilder();
		if (!ConfigHelper.getInstance().isEnableOffers()) {
			builder.append("No offers applied!");
			return builder.toString();
		}

		if (ConfigHelper.getInstance().isEnableApplesOffer()) {
			builder.append("[Offer on the Apples]");
		}

		if (ConfigHelper.getInstance().isEnableOrangesOffer()) {
			builder.append("[Offer on the Oranges]");

		}
		return builder.toString();
	}
}
