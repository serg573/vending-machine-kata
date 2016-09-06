package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tdd.message.Message;

public class VendingMachineTest {

    private VendingMachine machine = new VendingMachine();

    @Test
    public void checkPricesOfProductsByShelfNumber() {

        //wrong number of shelf
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(0)).isEqualTo(Message.NO_SHELF_WITH_THIS_NUMBER);
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(4)).isEqualTo(Message.NO_SHELF_WITH_THIS_NUMBER);

        //right number but only if there are always the same products at the same shelves
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(1)).isEqualTo("11");
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(2)).isEqualTo("8");
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(3)).isEqualTo("6");

    }

    @Test
    public void checkDenominations() {

        //wrong denomination
        Assertions.assertThat(Denominations.getCurrentDenomination("0")).isEqualTo(0.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("1.5")).isEqualTo(0.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("3")).isEqualTo(0.0f);

        //5, 2, 1, 0.5, 0.2, 0.1
        Assertions.assertThat(Denominations.getCurrentDenomination("5")).isEqualTo(5.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("2")).isEqualTo(2.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("1")).isEqualTo(1.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.5")).isEqualTo(0.5f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.2")).isEqualTo(0.2f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.1")).isEqualTo(0.1f);

    }

    @Test
    public void checkShelfNumbers() {

        //wrong numbers of shelves
        Assertions.assertThat(machine.setNumberOfShelf(-1)).isEqualTo(false);
        Assertions.assertThat(machine.setNumberOfShelf(4)).isEqualTo(false);
        Assertions.assertThat(machine.setNumberOfShelf(10)).isEqualTo(false);

        //test good numbers
        Assertions.assertThat(machine.setNumberOfShelf(1)).isEqualTo(true);
        Assertions.assertThat(machine.setNumberOfShelf(2)).isEqualTo(true);
        Assertions.assertThat(machine.setNumberOfShelf(3)).isEqualTo(true);

    }

    @Test
    public void checkGettingProductWithoutChange() {

        machine.setMachinesMoney("0.0");

        //to set shelf number to 3: "Mineral water 0.33l"
        Assertions.assertThat(machine.setNumberOfShelf(3)).isEqualTo(true);

        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse("1.0", "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("1")).isEqualTo(new MachineResponse(Message.GET_YOUR_PRODUCT, "Mineral water 0.33l", "0.0"));

        //check current machine money
        Assertions.assertThat(machine.getMachinesMoneyToString()).isEqualTo("6.0");

    }

    @Test
    public void checkGettingProductWithChange() {

        machine.setMachinesMoney("10.0");

        //to set shelf number to 2: "Chocolate bar"
        Assertions.assertThat(machine.setNumberOfShelf(2)).isEqualTo(true);

        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(Message.GET_YOUR_PRODUCT, "Chocolate bar", "2.0"));

    }

    public void checkCaseWhenThereIsNotEnoughChange() {

        machine.setMachinesMoney("0.0");

        //to set shelf number to 1: "Cola drink 0.25l"
        Assertions.assertThat(machine.setNumberOfShelf(1)).isEqualTo(true);

        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(Message.NO_CHANGE_TAKE_MONEY_BACK, "", "15.0"));

    }

    @Test
    public void checkInsufficientCase_cancelButton() {

        //to set shelf number to 1: "Cola drink 0.25l"
        Assertions.assertThat(machine.setNumberOfShelf(1)).isEqualTo(true);

        Assertions.assertThat(machine.insertNewDenomination("5")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("2")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));

        Assertions.assertThat(machine.cancelButton()).isEqualTo(new MachineResponse(Message.TAKE_YOUR_MONEY_BACK, "", "7.0"));

    }

}
