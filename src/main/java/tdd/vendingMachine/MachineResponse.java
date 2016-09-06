package tdd.vendingMachine;

/**
 * Created by serg on 9/2/16.
 */
public class MachineResponse {

    private String screenMessage;
    private String product;
    private String change;

    public MachineResponse(String screenMessage, String product, String change) {
        this.screenMessage = screenMessage;
        this.product = product;
        this.change = change;
    }

    public String getScreenMessage() {
        return screenMessage;
    }

    public String getProduct() {
        return product;
    }

    public String getChange() {
        return change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MachineResponse that = (MachineResponse) o;

        if (screenMessage != null ? !screenMessage.equals(that.screenMessage) : that.screenMessage != null)
            return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return change != null ? change.equals(that.change) : that.change == null;

    }

    @Override
    public int hashCode() {
        int result = screenMessage != null ? screenMessage.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (change != null ? change.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MachineResponse{" +
            "screenMessage='" + screenMessage + '\'' +
            ", product='" + product + '\'' +
            ", change='" + change + '\'' +
            '}';
    }
}
