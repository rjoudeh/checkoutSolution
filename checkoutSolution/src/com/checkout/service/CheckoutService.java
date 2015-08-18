package com.checkout.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import com.checkout.bean.CheckoutErrorResponse;
import com.checkout.bean.CheckoutResponse;
import com.checkout.bean.Product;
import com.checkout.errorHandling.CheckoutException;
import com.checkout.errorHandling.UndefinedProductException;
import com.checkout.xml.XmlUtil;

/**
 * Rest service used to checkout cart items!
 * 
 * @author Rania
 * 
 */

@Path("/checkout")
public class CheckoutService {

	@GET
	@Produces("application/xml")
	public Response checkoutItems(@QueryParam("items") String items) {

		try {
			if (items == null || items.isEmpty()) {
				return returnBadRequestError("Error Occured while checkout! Empty cart.");
			}

			String[] itemsArray = items.split(",");
			List<Product> products;
			products = ProcessCheckout.buildListOfProducts(itemsArray);
			BigDecimal total = new BigDecimal(0.0);
			// calculate the total of the items!
			total = ProcessCheckout.calculateTotal(products);

			// construct success response with the total, cart items and the
			// applied offers
			String xml = constructResponse(
					ProcessCheckout.getFormattedTotal(total),
					ProcessCheckout.printCartItems(products),
					ProcessCheckout.printAppliedOffers(products));

			return Response.status(Response.Status.OK).entity(xml).build();

		} catch (UndefinedProductException e) {
			e.printStackTrace();
			try {
				return returnBadRequestError("Error Occured while checkout! "
						+ e.getMessage());
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		} catch (JAXBException e) {
			e.printStackTrace();

		} catch (CheckoutException e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Error Occured while checkout! ").build();
	}

	/**
	 * 
	 * @param msg
	 * @return
	 * @throws JAXBException
	 */
	private Response returnBadRequestError(String msg) throws JAXBException {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(constructError(msg)).build();
	}

	/**
	 * Create response xml
	 * 
	 * @param formattedTotal
	 * @param cartItems
	 * @param appliedOffers
	 * @return
	 * @throws JAXBException
	 */
	private String constructResponse(String formattedTotal, String cartItems,
			String appliedOffers) throws JAXBException {
		// Prepare the bean data to be generate the xml
		CheckoutResponse response = new CheckoutResponse(formattedTotal,
				cartItems, appliedOffers);

		return XmlUtil.generateXml(response);
	}

	/**
	 * Create error xml
	 * 
	 * @param message
	 * @return
	 * @throws JAXBException
	 */
	private String constructError(String message) throws JAXBException {
		// Prepare the bean data to generate the xml
		CheckoutErrorResponse response = new CheckoutErrorResponse(message);
		return XmlUtil.generateXml(response);
	}
}
