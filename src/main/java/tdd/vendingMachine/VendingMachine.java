package tdd.vendingMachine;

import tdd.errorDescription.ErrorDescription;
import tdd.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VendingMachine {

    private ArrayList<Product> shelves = new ArrayList<>();

    private int currentShelfNumber = 0;
    private BigDecimal currentAmount = new BigDecimal("0.0");

    public VendingMachine() {

        shelves.add(new Product("Cola drink 0.25l", 3, "11"));
        shelves.add(new Product("Chocolate bar", 5, "8"));
        shelves.add(new Product("Mineral water 0.33l", 4, "6"));

    }

    public static void main(String[] args) {

        //System.out.print(new VendingMachine().setNumberOfShelf(1));
        //Denominations.getCurrentDenomination("5");
        //System.out.print(new VendingMachine().getCurretnAmountToScreen());

        VendingMachine machine = new VendingMachine();

        machine.setNumberOfShelf(3);

        machine.insertNewDenomination("1");
        machine.insertNewDenomination("2");
        machine.insertNewDenomination("2");

        machine.isMoneyEnough();

    }

    private boolean numberOfShelfIsCorrect(int shelfNumber) {
        return ((shelfNumber > 0) & (shelfNumber <= shelves.size()));
    }

    public BigDecimal getPriceByShelfNumber(int shelfNumber) {

        if (numberOfShelfIsCorrect(shelfNumber) == false) return new BigDecimal("0.0");

        return shelves.get(--shelfNumber).getPrice();

    }

    public String getProductNameByShelfNumber(int shelfNumber) {

        if (numberOfShelfIsCorrect(shelfNumber) == false) return "";

        return shelves.get(--shelfNumber).getName();

    }

    public String getPriceByShelfNumberToScreen(int shelfNumber) {

        BigDecimal bdValue = getPriceByShelfNumber(shelfNumber);

        if (bdValue.equals(new BigDecimal("0.0"))) {
            return ErrorDescription.NO_SHELF_WITH_THIS_NUMBER;
        } else {
            return bdValue.toString();
        }

    }

    public boolean setNumberOfShelf(int newNumber) {

        if (numberOfShelfIsCorrect(newNumber) == false) {
            currentShelfNumber = 0;
            return false;
        }

        currentShelfNumber = newNumber;
        return true;

    }

    public int getCurrentShelfNumber() {
        return currentShelfNumber;
    }

    public MachineResponse insertNewDenomination(String newDenumination) {

        float fValue = Denominations.getCurrentDenomination(newDenumination);
        if (fValue <= 0) {
            return new MachineResponse(ErrorDescription.WRONG_DENOMINATION, "", "0.0");
        }

        currentAmount = currentAmount.add(new BigDecimal(fValue));

        if (isMoneyEnough()) {
            return checkAndGiveOutTheProduct();
        } else {
            return new MachineResponse(showRestOfAmountAtScreen(), "", "0.0");
        }

    }

    public MachineResponse checkAndGiveOutTheProduct() {

        BigDecimal curPrice = getPriceByShelfNumber(currentShelfNumber);

        if (curPrice.equals(new BigDecimal("0.0"))) return new MachineResponse(ErrorDescription.CHOOSE_THE_SHELF, "", "0.0");

        BigDecimal restAmount = currentAmount.subtract(curPrice);

        switch (restAmount.compareTo(new BigDecimal("0.0"))) {

            case 0:
                //Enough money and there is no change
                return new MachineResponse(ErrorDescription.GET_YOUR_PRODUCT, getProductNameByShelfNumber(currentShelfNumber), "0.0");
            case 1:
                //Enough money and we have to give away the change
                return new MachineResponse(ErrorDescription.GET_YOUR_PRODUCT, getProductNameByShelfNumber(currentShelfNumber), restAmount.toString());
            case -1:
                //isn't enough money yet
                return new MachineResponse(showRestOfAmountAtScreen(), "", "0.0");
        }

        return new MachineResponse(ErrorDescription.CHOOSE_THE_SHELF, "", "0.0");

    }

    public String showRestOfAmountAtScreen() {

        BigDecimal curPrice = getPriceByShelfNumber(currentShelfNumber);

        if (curPrice.equals(new BigDecimal("0.0"))) return ErrorDescription.CHOOSE_THE_SHELF;

        BigDecimal restAmount = currentAmount.subtract(curPrice);

        if (restAmount.compareTo(new BigDecimal("0.0")) < 0) {
            //show amount that must be added to cover product price
            return restAmount.negate().toString();
        } else {
            //it should never happen
            return ErrorDescription.ERR_PRODUCT_MUST_HAVE_BEEN_GIVEN;
        }

    }

    public boolean isMoneyEnough() {

        //true - if successful
        //false - otherwise

        BigDecimal curPrice = getPriceByShelfNumber(currentShelfNumber);

        if (curPrice.equals(new BigDecimal("0.0"))) return false;

        if (currentAmount.compareTo(curPrice) >= 0) return true;

        return false;
    }

    public String getCurretnAmountToScreen() {
        return currentAmount.toString();
    }

}
