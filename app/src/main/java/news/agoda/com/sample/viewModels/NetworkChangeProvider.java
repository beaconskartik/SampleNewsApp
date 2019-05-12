package news.agoda.com.sample.viewModels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import news.agoda.com.sample.utils.NetworkUtils;

public class NetworkChangeProvider {

    private final Context context;
    private final NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;

    public NetworkChangeProvider(Context context) {
        this.context = context;
        this.networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        registerNetworkChangeBroadcast();
    }

    private void registerNetworkChangeBroadcast() {
        context.registerReceiver(networkChangeBroadcastReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unRegisterNetworkChangeBroadcast() {
        context.unregisterReceiver(networkChangeBroadcastReceiver);
    }

    Observable<Boolean> isNetworkConnected() {
        return networkChangeBroadcastReceiver
                .networkChangeObservable
                .distinctUntilChanged();
    }

    void release() {
        unRegisterNetworkChangeBroadcast();
    }

    public static class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

        private final BehaviorSubject<Boolean> networkChangeObservable = BehaviorSubject.create();

        @Override
        public void onReceive(Context context, Intent intent) {
                networkChangeObservable.onNext(NetworkUtils.isNetworkConnected(context));
        }
    }
}
