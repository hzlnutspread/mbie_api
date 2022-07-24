public class Data {

    private String tableName;
    private Item[] items;
    private String areaDefinition;
    private String periodCovered;

    public String getAreaDefinition() {
        return areaDefinition;
    }

    public void setAreaDefinition(String areaDefinition) {
        this.areaDefinition = areaDefinition;
    }

    public String getPeriodCovered() {
        return periodCovered;
    }

    public void setPeriodCovered(String periodCovered) {
        this.periodCovered = periodCovered;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
