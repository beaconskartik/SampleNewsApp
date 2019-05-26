package news.beacons.com.viewModels;

import news.beacons.com.views.NetworkChangeProvider;

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

    public VmNews getVmNews(NetworkChangeProvider networkChangeProvider) {
        if (vmNews == null) {
            vmNews =  new VmNews(networkChangeProvider);
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
