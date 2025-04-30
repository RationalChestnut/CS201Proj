import java.time.LocalDate;
import java.math.BigDecimal;

public class SalesData {
    private int store;
    private LocalDate date;
    private BigDecimal weeklySales;
    private boolean holidayFlag;
    private BigDecimal temperature;
    private BigDecimal fuelPrice;
    private BigDecimal cpi;
    private BigDecimal unemployment;

    public SalesData(int store,
                     LocalDate date,
                     BigDecimal weeklySales,
                     boolean holidayFlag,
                     BigDecimal temperature,
                     BigDecimal fuelPrice,
                     BigDecimal cpi,
                     BigDecimal unemployment) {
        this.store = store;
        this.date = date;
        this.weeklySales = weeklySales;
        this.holidayFlag = holidayFlag;
        this.temperature = temperature;
        this.fuelPrice = fuelPrice;
        this.cpi = cpi;
        this.unemployment = unemployment;
    }

    @Override
    public String toString() {
        return "SalesData{" +
               "store=" + store +
               ", date=" + date +
               ", weeklySales=" + weeklySales +
               ", holidayFlag=" + holidayFlag +
               ", temperature=" + temperature +
               ", fuelPrice=" + fuelPrice +
               ", cpi=" + cpi +
               ", unemployment=" + unemployment +
               '}';
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getWeeklySales() {
        return weeklySales;
    }

    public void setWeeklySales(BigDecimal weeklySales) {
        this.weeklySales = weeklySales;
    }

    public boolean isHolidayFlag() {
        return holidayFlag;
    }

    public void setHolidayFlag(boolean holidayFlag) {
        this.holidayFlag = holidayFlag;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(BigDecimal fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public BigDecimal getCpi() {
        return cpi;
    }

    public void setCpi(BigDecimal cpi) {
        this.cpi = cpi;
    }

    public BigDecimal getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(BigDecimal unemployment) {
        this.unemployment = unemployment;
    }
}
