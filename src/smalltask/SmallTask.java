package smalltask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Small task:
 * 1) print out products with type BED ordered by name (bonus with different orderings)
 * 2) sum up the price per types 
 * 3) avg the price per types
 * 
 */
public class SmallTask {

    private static final String[] PRODUCT_TYPES = { "SOFA", "BED", "CHAIR" };

    static void main(String[] params) {
        List<Product> productList = Generator.getRandomProductList();

        //--- [START HERE] -----------------------------------------
    }

    private static class Product {

        public final String productType;
        public final String productName;
        public double price = 0.0;

        Product(String productName, String productType, double price) {
            this.productName = productName;
            this.productType = productType;
            this.price = price;
        }

    }

    private static class Generator {

        private static final char[] ALPHA = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E'
                , 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        private static Random RANDOM = new Random();

        private static String getRandomAlphaStringWithFixedLength(int length) {
            StringBuffer b = new StringBuffer();
            for (int i = 0; i < length; i++) {
                b.append(ALPHA[RANDOM.nextInt(ALPHA.length)]);
            }
            return b.toString();
        }

        public static List<Product> getRandomProductList() {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                productList.add(new Product(getRandomAlphaStringWithFixedLength(10), PRODUCT_TYPES[RANDOM.nextInt(3)], RANDOM.nextDouble() * 100));
            }
            return productList;
        }
    }

}
