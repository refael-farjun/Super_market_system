package BusinessLayer;

import BusinessLayer.Inventory.FacadeInv;
import BusinessLayer.Supplier.FacadeSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the Facade of the system.
 * This class hold two more facade for each package: suppliers and inventory
 */

public class FacadeController {
    private static FacadeController facadeController = null;
    public FacadeInv facadeInv;
    public FacadeSupplier facadeSupplier;
    public BusinessLayer.DeliveryBusinessLayer.FacadeController facadeDelivery;

    private FacadeController() {
        facadeInv = FacadeInv.getInstance();
        facadeSupplier = FacadeSupplier.getInstance();
        facadeDelivery = BusinessLayer.DeliveryBusinessLayer.FacadeController.getInstance();
    }

    public static FacadeController getInstance() {
        if (facadeController == null)
            facadeController = new FacadeController();

        return facadeController;
    }

    // after each order we send info to match delivery.
    public void sendOrderToDelivery(int orderID, int suppID, boolean pickup) {
        HashMap<Integer,Integer> prodQuantity = facadeSupplier.getOrderController().orders.get(orderID).getProducts();
        //Supplier details:
        String address = facadeSupplier.getSupplierController().getSuppliers().get(suppID).getAddress();
        String infoSupplyDates = facadeSupplier.getSupplierController().getSuppliers().get(suppID).getInfoSupplyDay();
        // TODO: delivery module need to continue from here.
    }

    public ResponseT<StringBuilder> ordersByLack(HashMap<Integer, Integer> stkReport){
        try{
            ResponseT<StringBuilder> s = facadeSupplier.ordersByLack(stkReport);

            List<Integer> orders = new ArrayList<>();
            String[] strArr =  s.value.toString().split(" ");
            for(int i=0; i<strArr.length; i++){
                if(strArr[i].equals("OrderID:")){
                    // each order id is added to list of orders
                    orders.add(Integer.parseInt(strArr[i+1]));
                }
            }

            // for each order we sent the specific details to delivery module.
            // SupplierID,orderID,PickUp
            for(Integer order : orders){
                int SupplierID = facadeSupplier.getOrderController().getOrders().get(order).getSupplierID();
                boolean isPickUp = facadeSupplier.getSupplierController().needPickUp(SupplierID);
                sendOrderToDelivery(order,SupplierID,isPickUp);
            }
            return s;

        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
}