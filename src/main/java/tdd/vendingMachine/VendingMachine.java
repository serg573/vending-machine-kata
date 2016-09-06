package tdd.vendingMachine;

import tdd.message.Message;
import tdd.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VendingMachine {

    private ArrayList<Product> shelves = new ArrayList<>();

    private int currentShelfNumber = 0;
    private BigDecimal currentAmount = new BigDecimal("0.0");
    private BigDecimal machinesMoney = new BigDecimal("0.0");

    private boolean wasPutWrongDenomination = false;

    public VendingMachine() {

        shelves.add(new Product("Cola drink 0.25l", 3, "11"));
        shelves.add(new Product("Chocolate bar", 5, "8"));
        shelves.add(new Product("Mineral water 0.33l", 4, "6"));

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
            return Message.NO_SHELF_WITH_THIS_NUMBER;
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

        if (wasPutWrongDenomination) return new MachineResponse(Message.WRONG_DENOMINATION, "", "0.0");

        float fValue = Denominations.getCurrentDenomination(newDenumination);

        if (fValue <= 0) {
            wasPutWrongDenomination = true;
            return new MachineResponse(Message.WRONG_DENOMINATION, "", "0.0");
        }

        currentAmount = currentAmount.add(new BigDecimal(fValue));

        if (isMoneyEnough()) {
            return checkAndGiveOutTheProduct();
        } else {
            return new MachineResponse(showRestOfAmountAtScreen(), "", "0.0");
        }

    }

    private MachineResponse checkAndGiveOutTheProduct() {

        BigDecimal curPrice = getPriceByShelfNumber(currentShelfNumber);

        if (curPrice.equals(new BigDecimal("0.0"))) return new MachineResponse(Message.CHOOSE_THE_SHELF, "", "0.0");

        BigDecimal restAmount = currentAmount.subtract(curPrice);

        switch (restAmount.compareTo(new BigDecimal("0.0"))) {

            case 0:
                //Enough money and there is no change
                machinesMoney = machinesMoney.add(currentAmount);
                currentAmount = new BigDecimal("0.0");
                return new MachineResponse(Message.GET_YOUR_PRODUCT, getProductNameByShelfNumber(currentShelfNumber), "0.0");
            case 1:
                //Enough money and we have to check and give away the change
                if (machinesMoney.compareTo(restAmount) >= 0) {
                    machinesMoney = machinesMoney.add(currentAmount).subtract(restAmount);
                    currentAmount = new BigDecimal("0.0");
                    return new MachineResponse(Message.GET_YOUR_PRODUCT, getProductNameByShelfNumber(currentShelfNumber), restAmount.toString());
                } else {
                    MachineResponse response = new MachineResponse(Message.NO_CHANGE_TAKE_MONEY_BACK, "", currentAmount.toString());
                    currentAmount = new BigDecimal("0.0");
                    return response;
                }

            case -1:
                //isn't enough money yet
                return new MachineResponse(showRestOfAmountAtScreen(), "", "0.0");
        }

        return new MachineResponse(Message.CHOOSE_THE_SHELF, "", "0.0");

    }

    public String showRestOfAmountAtScreen() {

        BigDecimal curPrice = getPriceByShelfNumber(currentShelfNumber);

        if (curPrice.equals(new BigDecimal("0.0"))) return Message.CHOOSE_THE_SHELF;

        BigDecimal restAmount = currentAmount.subtract(curPrice);

        if (restAmount.compareTo(new BigDecimal("0.0")) < 0) {
            //show amount that must be added to cover product price
            return restAmount.negate().toString();
        } else {
            //it should never happen
            return Message.ERR_PRODUCT_MUST_HAVE_BEEN_GIVEN;
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

    public MachineResponse cancelButton() {

        MachineResponse response = new MachineResponse(Message.TAKE_YOUR_MONEY_BACK, "", currentAmount.toString());

        currentAmount = new BigDecimal("0.0");
        wasPutWrongDenomination = false;

        return response;

    }

    public String getMachinesMoneyToString() {
        return machinesMoney.toString();
    }

    public void setMachinesMoney(String machinesMoney) {
        this.machinesMoney = new BigDecimal(machinesMoney);
    }
}
