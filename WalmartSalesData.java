import java.time.LocalDate;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface WalmartSalesData<T> {

    /**
     * Loads all sales data from the specified file into the data structure.
     *
     * @param filePath the path to the CSV file containing sales data
     */
      public int loadDataset(String filePath)throws IOException;


    /**
     * Returns all sales records for the specified store on the given date.
     *
     * @param storeId the store number to query
     * @param date    the sales date to match (YYYY‑MM‑DD, inclusive)
     * @return a list of sales records for that store on that date
     */
    List<T> exactMatchQuery(int storeId, LocalDate date) throws IOException;

    /**
     * Returns all sales records for the specified store between the given dates
     * where weekly sales fall between the specified bounds.
     *
     * @param storeId        the store number to query
     * @param startDate      the start date of the period (inclusive)
     * @param endDate        the end date of the period (inclusive)
     * @param minWeeklySales the minimum Weekly_Sales value (inclusive)
     * @param maxWeeklySales the maximum Weekly_Sales value (inclusive)
     * @return a list of sales records matching store, date, and sales criteria
     */
    List<T> rangeQuery(
        int storeId,
        LocalDate startDate,
        LocalDate endDate,
        Long minWeeklySales,
        Long maxWeeklySales
    );

    /**
     * Returns all sales records for the specified store between the given dates
     * where weekly sales do not exceed the specified bound.
     *
     * @param storeId        the store number to query
     * @param startDate      the start date of the period (inclusive)
     * @param endDate        the end date of the period (inclusive)
     * @param maxWeeklySales the maximum Weekly_Sales value (inclusive)
     * @return a list of sales records matching store, date, and maximum sales criteria
     */
    List<T> rangeQuery(
        int storeId,
        LocalDate startDate,
        LocalDate endDate,
        Long maxWeeklySales
    );

    /**
     * Calculates the average weekly sales for the specified store over entire data range
     *
     * @param storeId   the store number to query
     * Returns data as float

     */
    Float averageQuery(
        int storeId
    );
}
