package dist.purify.air.utils;

/**
 * Created by sunshine on 16/9/19.
 */
public class GoodsUtil {
    public static double calculateOrderFee(double price, int quantity) {
        int tPrice = ((int) price * 100);
        int tTotal = tPrice * quantity;
        return tTotal * 1.0 / 100;
    }
}
