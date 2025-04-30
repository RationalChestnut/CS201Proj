import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WalmartSalesDataImpl dataImpl = new WalmartSalesDataImpl();

        try {
            int count = dataImpl.loadDataset("./salesData.csv");
            System.out.println("Records loaded: " + count);

            // Exact Match
            System.out.println("\nExact match for store 1 on 2010-02-05:");
            List<SalesData> exactMatches = dataImpl.exactMatchQuery(1, LocalDate.of(2010, 2, 5));
            for (SalesData data : exactMatches) {
                System.out.println(data);
            }

            // Range Query with min and max weekly sales
            System.out.println("\nRange query for store 1 from 2010-02-05 to 2010-03-26 with sales between 1.45M and 1.65M:");
            List<SalesData> rangeWithMinMax = dataImpl.rangeQuery(
                1,
                LocalDate.of(2010, 2, 5),
                LocalDate.of(2010, 3, 26),
                1450000L,
                1650000L
            );
            for (SalesData data : rangeWithMinMax) {
                System.out.println(data);
            }

            // Range Query with only max weekly sales
            System.out.println("\nRange query for store 1 from 2010-02-05 to 2010-03-26 with sales <= 1.5M:");
            List<SalesData> rangeWithMaxOnly = dataImpl.rangeQuery(
                1,
                LocalDate.of(2010, 2, 5),
                LocalDate.of(2010, 3, 26),
                1500000L
            );
            for (SalesData data : rangeWithMaxOnly) {
                System.out.println(data);
            }

            // Average
            System.out.println("\nAverage weekly sales for store 1:");
            System.out.println(dataImpl.averageQuery(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
