package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import tdd.errorDescription.ErrorDescription;

public class VendingMachineTest {

    VendingMachine machine = new VendingMachine();

    @Test
    public void checkPricesOfProductsByShelfNumber() {

        //wrong number of shelf
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(0)).isEqualTo(ErrorDescription.NO_SHELF_WITH_THIS_NUMBER);
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(4)).isEqualTo(ErrorDescription.NO_SHELF_WITH_THIS_NUMBER);

        //right number but only if there are always the same products at the same shelves
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(1)).isEqualTo("11");
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(2)).isEqualTo("8");
        Assertions.assertThat(machine.getPriceByShelfNumberToScreen(3)).isEqualTo("6");

    }

    @Test
    public void checkDenominations() {

        //wrong denomination
        Assertions.assertThat(Denominations.getCurrentDenomination("0")).isEqualTo(0.0f);

        //5, 2, 1, 0.5, 0.2, 0.1
        Assertions.assertThat(Denominations.getCurrentDenomination("5")).isEqualTo(5.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("2")).isEqualTo(2.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("1")).isEqualTo(1.0f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.5")).isEqualTo(0.5f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.2")).isEqualTo(0.2f);
        Assertions.assertThat(Denominations.getCurrentDenomination("0.1")).isEqualTo(0.1f);

    }

    @Test
    public void checkRestOfAmountOfPrice() {

        //to start with we should choose a shelf

        //test wrong numbers
        Assertions.assertThat(machine.setNumberOfShelf(-1)).isEqualTo(false);
        Assertions.assertThat(machine.setNumberOfShelf(10)).isEqualTo(false);

        //test good numbers
        Assertions.assertThat(machine.setNumberOfShelf(1)).isEqualTo(true);
        Assertions.assertThat(machine.setNumberOfShelf(2)).isEqualTo(true);
        Assertions.assertThat(machine.setNumberOfShelf(3)).isEqualTo(true);

        //well, for now our current shelf number is 3
        //let's start insert money

        //test with wrong denominations
        Assertions.assertThat(machine.insertNewDenomination("10")).isEqualTo(new MachineResponse(ErrorDescription.WRONG_DENOMINATION, "", "0.0"));
        Assertions.assertThat(machine.insertNewDenomination("3")).isEqualTo(new MachineResponse(ErrorDescription.WRONG_DENOMINATION, "", "0.0"));
        Assertions.assertThat(machine.getCurretnAmountToScreen()).isEqualTo("0.0");

        //test with right denominations
        Assertions.assertThat(machine.insertNewDenomination("1")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
            Assertions.assertThat(machine.isMoneyEnough()).isEqualTo(false);
            Assertions.assertThat(machine.showRestOfAmountAtScreen()).isEqualTo("5.0");

        Assertions.assertThat(machine.insertNewDenomination("2")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
            Assertions.assertThat(machine.isMoneyEnough()).isEqualTo(false);
            Assertions.assertThat(machine.showRestOfAmountAtScreen()).isEqualTo("3.0");

        Assertions.assertThat(machine.insertNewDenomination("2")).isEqualTo(new MachineResponse(machine.showRestOfAmountAtScreen(), "", "0.0"));
            Assertions.assertThat(machine.isMoneyEnough()).isEqualTo(false);
            Assertions.assertThat(machine.showRestOfAmountAtScreen()).isEqualTo("1.0");

        Assertions.assertThat(machine.insertNewDenomination("2")).isEqualTo(new MachineResponse(ErrorDescription.GET_YOUR_PRODUCT, "Mineral water 0.33l", "1.0"));

    }

}
