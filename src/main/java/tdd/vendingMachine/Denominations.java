package tdd.vendingMachine;

import java.util.HashMap;

/**
 * Created by serg on 9/1/16.
 */
public class Denominations {

    //We will use a map for check denominations
    private static final HashMap<String, Float> mapDenuminations = new HashMap<>();

    static {
        //Machine accepts denominations 5, 2, 1, 0.5, 0.2, 0.1.
        mapDenuminations.put("5", 5.0f);
        mapDenuminations.put("2", 2.0f);
        mapDenuminations.put("1", 1.0f);
        mapDenuminations.put("0.5", 0.5f);
        mapDenuminations.put("0.2", 0.2f);
        mapDenuminations.put("0.1", 0.1f);
    }

    public static float getCurrentDenomination(String testDenumination) {

        Float fValue = mapDenuminations.get(testDenumination);

        if (fValue == null) {
            return 0.0f;
        } else {
            return fValue;
        }

    }

}
