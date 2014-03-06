package review;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import review.rest.Inventory;
import review.rest.QcOrderObject;
import review.rest.dbHandler;

public class QcOrderClass {

	/**
	 * QC - quality check
	 * InventoryItem - a produced product
	 * 
	 * Get all inventory that needs QC, 
	 * calculates the total purchase price, avg price and quantity 
	 * 
	 * @return HashMap<orderId, inventoryList>
	 */
	public HashMap<String, Object> fetchAllInventory() throws Exception {
		List<QcOrderObject> l = dbHandler.getAllQcOrder();
		HashMap<String, List> itemListPerOrder = new HashMap<>();
		int totalQuantity = 0;
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		for (QcOrderObject qco : l) {
			//some checks
			if (qco.getStatus()=="CANCELLED" || qco.getStatus()=="REJECTED")
				continue;
			
			if (qco.responsibleParty!="Company") {
				throw new Exception("Error the order should belong to us!");
			}
			
			itemListPerOrder.put("orderId", dbHandler.getInventoryForOrder(qco.getOrderId()));
			sumUpTotalQuantityAndPrice(dbHandler.getInventoryForOrder(qco.getOrderId()), totalQuantity, totalPrice);
		}
		
	
		HashMap<String, Object> result = new HashMap<>();
		result.put("itemList", itemListPerOrder);
		result.put("totalQuantity", totalQuantity);
		result.put("totalPrice", totalPrice);
		result.put("avgPrice", totalPrice.doubleValue()/totalQuantity);
		
		return result;
	}

	/** Sum up total quantity and price */
	private void sumUpTotalQuantityAndPrice(List<Inventory> inventoryForOrder, int totalQuantity, BigDecimal totalPrice) {
		for (Inventory i : inventoryForOrder) {
			totalQuantity++;
			totalPrice.add(i.getPrice());
		}
	}
	

	
	
	
}
