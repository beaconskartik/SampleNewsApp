package news.beacons.com.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import news.beacons.com.utils.NetworkUtils;

public class NetworkChangeProvider {

    private final Context context;
    private final NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;

    NetworkChangeProvider(Context context) {
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

    public Observable<Boolean> isNetworkConnected() {
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
