package news.agoda.com.sample.viewModels;

public class VmLocator {

    private static VmLocator vmLocator;
    private VmNews vmNews;
    private VmDetailedNews vmDetailedNews;

    public static VmLocator getInstance() {
        if (vmLocator != null) {
            return vmLocator;
        }
        synchronized(VmLocator.class) {
            if (vmLocator == null) {
                vmLocator = new VmLocator();
            }
        }
        return vmLocator;
    }

    private VmLocator() { }

    public VmNews getVmNews() {
        if (vmNews == null) {
            vmNews =  new VmNews();
        }
        return vmNews;
    }

    public VmDetailedNews getVmDetailedNews() {
        if (vmDetailedNews == null) {
            vmDetailedNews = new VmDetailedNews();
        }
        return vmDetailedNews;
    }
}
