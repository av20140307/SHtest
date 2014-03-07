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
     * calculates the number of product passed/failed previous QCs 
     * 
     * @return HashMap<orderId, inventoryList>
     */
    public HashMap<String, Object> fetchAllInventory() throws Exception {
        List<QcOrderObject> l = dbHandler.getAllQcOrder();
        HashMap<String, List> itemListPerOrder = new HashMap<>();
        int numOfItemsPassedQC = 0;
        int numOfItemsFailedQC = 0;
        BigDecimal passedQCValue = BigDecimal.ZERO;

        for (QcOrderObject qco : l) {
            //some checks
            if (qco.getStatus() == "CANCELLED" || qco.getStatus() == "REJECTED")
                continue;

            if (qco.responsibleParty != "Company") {
                throw new Exception("Error the order should belong to us!");
            }

            itemListPerOrder.put("orderId", dbHandler.getInventoryForOrder(qco.getOrderId()));
            sumUpTotalQuantityAndValue(dbHandler.getInventoryForOrder(qco.getOrderId()), numOfItemsPassedQC, numOfItemsFailedQC, passedQCValue);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("itemList", itemListPerOrder);
        result.put("numOfItemsPassedQC", numOfItemsPassedQC);
        result.put("numOfItemsFailedQC", numOfItemsFailedQC);
        result.put("passedQCValue", passedQCValue);
        

        return result;
    }

    /** Sum up total quantity and price */
    private void sumUpTotalQuantityAndValue(List<Inventory> inventoryForOrder, int numOfItemsPassedQC, int numOfItemsFailedQC, BigDecimal passedQCValue) {
        for (Inventory i : inventoryForOrder) {
            if (i.failedQC())
                numOfItemsFailedQC++;
            else
                numOfItemsPassedQC++;
                passedQCValue.add(i.getPrice());
        }
    }

}
