package com.liuyihui.wifip2pdemo;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyihui.mylibrary.MyThreadHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * wifi p2p/Direct demo
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    ListView deviceListView;
    TextView deviceInfoTextView;
    Switch discoverSwitcher;
    TextView connectStatusTextView;
    Button disconnectButton;

    private MyReceiver receiver;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private List<WifiP2pDevice> p2pDeviceList;
    private ArrayAdapter<WifiP2pDevice> adapter;
    private boolean isDiscover;
    public DisplayManager displayManager;


    public static MyThreadHandler<MainActivity> mHandler =
            new MyThreadHandler<>(new MyThreadHandler.IMessageProcessor<MainActivity>() {
        @Override
        public void processMessage(MainActivity mainActivity, Message msg) {

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                          Manifest.permission.CHANGE_WIFI_STATE,
                          Manifest.permission.ACCESS_WIFI_STATE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE,
                          Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        mHandler.setTargetInstance(this);
        receiver = new MyReceiver();
        p2pDeviceList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p2pDeviceList);

        disconnectButton = findViewById(R.id.disconnect);
        connectStatusTextView = findViewById(R.id.connectStatus);
        deviceInfoTextView = findViewById(R.id.device_info);
        deviceListView = findViewById(R.id.listview);
        deviceListView.setAdapter(adapter);
        discoverSwitcher = findViewById(R.id.discoverStatus);
        discoverSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discoverSwitcher.isChecked()) {
                    startDiscover();
                } else {
                    stopDiscover();
                }
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                wifiP2pManager.removeGroup(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "removeGroup onSuccess: ");
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.d(TAG, "removeGroup onFailure: " + reason);
                    }
                });
            }
        });

        //wifiP2pManager 初始化
        wifiP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        if (wifiP2pManager != null) {

            channel = wifiP2pManager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
                @Override
                public void onChannelDisconnected() {
                    Log.d(TAG, "onChannelDisconnected: ");
                }
            });
        }

        String uuid = UUID.randomUUID().toString();
        List<String> services = new ArrayList<>();
        services.add("sv1");
        services.add("sv2");
        WifiP2pUpnpServiceInfo wifiP2pUpnpServiceInfo = WifiP2pUpnpServiceInfo.newInstance(uuid, "vivo", services);
        wifiP2pManager.addLocalService(channel, wifiP2pUpnpServiceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "addLocalService onSuccess: ");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "addLocalService onFailure: " + reason);
            }
        });

        WifiP2pUpnpServiceRequest wifiP2pUpnpServiceRequest = WifiP2pUpnpServiceRequest.newInstance();
        wifiP2pManager.addServiceRequest(channel, wifiP2pUpnpServiceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "addServiceRequest onSuccess: ");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "addServiceRequest onFailure: ");
            }
        });


        wifiP2pManager.setUpnpServiceResponseListener(channel, new WifiP2pManager.UpnpServiceResponseListener() {
            @Override
            public void onUpnpServiceAvailable(List<String> uniqueServiceNames, WifiP2pDevice srcDevice) {
                Log.d(TAG, "onUpnpServiceAvailable: " + srcDevice.deviceName);
            }
        });


        //register receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        registerReceiver(receiver, intentFilter);

        //item点击
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiP2pDevice device = adapter.getItem(position);
                //0:CONNECTED 1:INVITED 2:FAILED 3:AVAILABLE 4:UNAVAILABLE
                Log.d(TAG, "onItemClick: " + String.format("%s", device.status));
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                config.groupOwnerIntent = WifiP2pConfig.GROUP_OWNER_BAND_AUTO;
                wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "connect onSuccess: ");
                        //这里，对方peer一般还未接受邀请。去广播里检测对方是否接受了邀请，链接成功，状态
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.d(TAG, "connect onFailure: ");
                    }
                });

            }
        });

    }

    public void startDiscover() {
        //启动发现 点
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "discoverPeers onSuccess: ");
            }

            @Override
            public void onFailure(int reason) {
                //0:ERROR,1:NO_SUPPORT,2:BUSY
                Log.d(TAG, "discoverPeers onFailure: " + reason);
            }
        });

        // 启动发现 Service. 这里决定被发现？
        /*wifiP2pManager.discoverServices(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "discoverServices onSuccess: ");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "discoverServices onFailure: ");
            }
        });*/
    }

    public void stopDiscover() {
        if (wifiP2pManager != null) {
            wifiP2pManager.stopPeerDiscovery(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "stopPeerDiscovery onSuccess: ");
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "stopPeerDiscovery onFailure: ");
                }
            });
        }
    }

    public void updateDiscoverState(boolean isDiscover) {
        discoverSwitcher.setChecked(isDiscover);
    }

    public void updateConnectStatus(boolean connected) {
        connectStatusTextView.setText(connected ? "已连接" : "未连接");
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION://最先收到这个广播，在这里检测p2p功能
                    boolean p2pIsEnable = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,
                                                             WifiP2pManager.WIFI_P2P_STATE_DISABLED) == WifiP2pManager.WIFI_P2P_STATE_ENABLED;
                    Log.d(TAG, "onReceive: p2pIsEnable=" + p2pIsEnable);
                    break;
                case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                    Log.d(TAG, "onReceive: WIFI_P2P_PEERS_CHANGED_ACTION");
                    //获取到设备列表信息
                    WifiP2pDeviceList peers = intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);
                    p2pDeviceList.clear(); //清除旧的信息
                    p2pDeviceList.addAll(peers.getDeviceList()); //更新信息
                    adapter.notifyDataSetChanged();  //更新列表

                    break;
                case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                    Log.d(TAG, "onReceive: WIFI_P2P_CONNECTION_CHANGED_ACTION");

                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                    WifiP2pInfo wifiP2pInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
                    WifiP2pGroup wifiP2pGroup = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);

                    Log.d(TAG, "连接变化: " + (networkInfo.isConnected() ? "已连接" : "未连接"));
                    updateConnectStatus(networkInfo.isConnected());
                    //检测到链接成功：
                    if (networkInfo.isConnected()) {
                        Log.d(TAG, String.format("成组:%s. %s", wifiP2pInfo.groupFormed, wifiP2pInfo.toString()));

                        //todo 连接后基于socket通信
                        if (wifiP2pInfo.isGroupOwner) {
                            SocketTrasmitManager.getInstance().initServerSocket();
                        } else {
                            SocketTrasmitManager.getInstance()
                                                .initClientSocket(wifiP2pInfo.groupOwnerAddress.getHostAddress());
                        }

                    }

                    break;
                case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                    Log.d(TAG, "onReceive: WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
                    WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
                    deviceInfoTextView.setText("deviceInfo:" + String.format("%s\n%s\n%s\n%s",
                                                                             device.deviceName,
                                                                             device.primaryDeviceType,
                                                                             device.deviceAddress,
                                                                             device.secondaryDeviceType));
                    break;
                case WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION:
                    int discoveryState = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE,
                                                            WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED);
                    isDiscover = discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED;
                    Log.d(TAG, "onReceive: WIFI_P2P_DISCOVERY_CHANGED_ACTION " + isDiscover);
                    updateDiscoverState(isDiscover);
                    break;
            }
        }
    }

    public void pressSend(View view) {
        SocketTrasmitManager.getInstance().handleSend();
    }

    public void pressClose(View view) {
        SocketTrasmitManager.getInstance().closeSocket();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        stopDiscover();
        pressClose(null);
    }


    /**
     * 以下4个方法，使用PermissionGen 框架，针对android 6.x sdk 获取系统某些权限
     * by liuyihui
     *
     * @param activity    活动实例
     * @param permissions 不定长权限数组
     */
    public void getPermission(Activity activity, String... permissions) {
        PermissionGen.with(activity).addRequestCode(100).permissions(permissions).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void onSuccess() {
        //ToastUtil.toast("已获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取设备读写权限失败", Toast.LENGTH_SHORT).show();
    }

}
