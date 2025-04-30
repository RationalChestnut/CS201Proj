import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WalmartSalesDataImpl implements WalmartSalesData<SalesData> {

    private final Map<Integer, TreeMap<LocalDate, List<SalesData>>> storeData = new HashMap<>();
    private final Map<Integer, BigDecimal> totalSalesPerStore = new HashMap<>();
    private final Map<Integer, Integer> countPerStore = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public int loadDataset(String filePath) throws IOException {
        int recordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
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

                SalesData record = new SalesData(store, date, weeklySales, holidayFlag, temperature, fuelPrice, cpi, unemployment);

                // Insert into TreeMap index
                storeData.computeIfAbsent(store, k -> new TreeMap<>())
                         .computeIfAbsent(date, k -> new ArrayList<>())
                         .add(record);

                // Track totals for average
                totalSalesPerStore.merge(store, weeklySales, BigDecimal::add);
                countPerStore.merge(store, 1, Integer::sum);

                recordCount++;
            }
        }

        return recordCount;
    }

    @Override
    public List<SalesData> exactMatchQuery(int storeId, LocalDate date) {
        if (!storeData.containsKey(storeId)) return Collections.emptyList();
        return storeData.get(storeId).getOrDefault(date, Collections.emptyList());
    }

    @Override
    public List<SalesData> rangeQuery(int storeId, LocalDate startDate, LocalDate endDate, Long minWeeklySales, Long maxWeeklySales) {
        if (!storeData.containsKey(storeId)) return Collections.emptyList();
        TreeMap<LocalDate, List<SalesData>> dateMap = storeData.get(storeId);

        List<SalesData> result = new ArrayList<>();
        for (Map.Entry<LocalDate, List<SalesData>> entry : dateMap.subMap(startDate, true, endDate, true).entrySet()) {
            for (SalesData record : entry.getValue()) {
                long sales = record.getWeeklySales().longValue();
                if (sales >= minWeeklySales && sales <= maxWeeklySales) {
                    result.add(record);
                }
            }
        }
        return result;
    }

    @Override
    public List<SalesData> rangeQuery(int storeId, LocalDate startDate, LocalDate endDate, Long maxWeeklySales) {
        if (!storeData.containsKey(storeId)) return Collections.emptyList();
        TreeMap<LocalDate, List<SalesData>> dateMap = storeData.get(storeId);

        List<SalesData> result = new ArrayList<>();
        for (Map.Entry<LocalDate, List<SalesData>> entry : dateMap.subMap(startDate, true, endDate, true).entrySet()) {
            for (SalesData record : entry.getValue()) {
                if (record.getWeeklySales().longValue() <= maxWeeklySales) {
                    result.add(record);
                }
            }
        }
        return result;
    }

    @Override
    public Float averageQuery(int storeId) {
        if (!totalSalesPerStore.containsKey(storeId)) return 0f;
        BigDecimal total = totalSalesPerStore.get(storeId);
        int count = countPerStore.getOrDefault(storeId, 1);
        return total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP).floatValue();
    }
}
