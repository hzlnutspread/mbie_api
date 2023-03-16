public class Item {

    public String label;
    public String code;
    public String dwell;
    public String area;
    public int med;
    public float lmean;
    public int lq;
    public Object nBedrms;
    public int uq;
    public int sd;
    public int nClosed;
    public float brr;
    public int slq;
    public int suq;
    public int nCurr;
    public int mean;
    public float lsd;
    public int nLodged;

    public Item() {

    }

    public Item(String label, String code) {
        this.label = label;
        this.code = code;
    }

    public Item(int med, String dwell, float lmean, int lq, Object nBedrms, int uq, int sd, int nClosed,
                float brr, int slq, int suq, int nCurr, String area, int mean, float lsd, int nLodged) {
        this.med = med;
        this.dwell = dwell;
        this.lmean = lmean;
        this.lq = lq;
        this.nBedrms = nBedrms;
        this.uq = uq;
        this.sd = sd;
        this.nClosed = nClosed;
        this.brr = brr;
        this.slq = slq;
        this.suq = suq;
        this.nCurr = nCurr;
        this.area = area;
        this.mean = mean;
        this.lsd = lsd;
        this.nLodged = nLodged;
    }

    @Override
    public String toString() {
        return "label: " + label + ", " + "code: " + code;
    }
}
