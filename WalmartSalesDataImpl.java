import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WalmartSalesDataImpl implements WalmartSalesData<SalesData> {

    private final List<SalesData> allRecords = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public int loadDataset(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] tok = line.split(",");
                if (tok.length < 8) continue;

                int store = Integer.parseInt(tok[0]);
                LocalDate date = LocalDate.parse(tok[1], formatter);
                BigDecimal weeklySales = new BigDecimal(tok[2]);
                boolean holidayFlag = tok[3].trim().equals("1");
                BigDecimal temperature = new BigDecimal(tok[4]);
                BigDecimal fuelPrice = new BigDecimal(tok[5]);
                BigDecimal cpi = new BigDecimal(tok[6]);
                BigDecimal unemployment = new BigDecimal(tok[7]);

                SalesData record = new SalesData(
                    store, date, weeklySales, holidayFlag,
                    temperature, fuelPrice, cpi, unemployment
                );
                allRecords.add(record);
            }
        }
        return allRecords.size();
    }

    @Override
    public List<SalesData> exactMatchQuery(int storeId, LocalDate date) {
        return allRecords.stream()
                .filter(r -> r.getStore() == storeId && r.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesData> rangeQuery(int storeId, LocalDate startDate, LocalDate endDate, Long minWeeklySales, Long maxWeeklySales) {
        return allRecords.stream()
                .filter(r -> r.getStore() == storeId &&
                             !r.getDate().isBefore(startDate) &&
                             !r.getDate().isAfter(endDate) &&
                             r.getWeeklySales().longValue() >= minWeeklySales &&
                             r.getWeeklySales().longValue() <= maxWeeklySales)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesData> rangeQuery(int storeId, LocalDate startDate, LocalDate endDate, Long maxWeeklySales) {
        return allRecords.stream()
                .filter(r -> r.getStore() == storeId &&
                             !r.getDate().isBefore(startDate) &&
                             !r.getDate().isAfter(endDate) &&
                             r.getWeeklySales().longValue() <= maxWeeklySales)
                .collect(Collectors.toList());
    }

    @Override
    public Float averageQuery(int storeId) {
        List<BigDecimal> sales = allRecords.stream()
                .filter(r -> r.getStore() == storeId)
                .map(SalesData::getWeeklySales)
                .collect(Collectors.toList());

        if (sales.isEmpty()) return 0f;

        BigDecimal total = sales.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.divide(BigDecimal.valueOf(sales.size()), 2, RoundingMode.HALF_UP).floatValue();
    }
}
