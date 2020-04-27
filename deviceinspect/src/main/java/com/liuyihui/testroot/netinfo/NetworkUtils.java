package com.liuyihui.testroot.netinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_GOOD;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_GREAT;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_MODERATE;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_POOR;

/**
 * Created by gaowen on 2017/7/6.
 */

public class NetworkUtils {
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static NetworkType getNetworkType(Context context) {
        NetworkType netType = NetworkType.UNKNOWN;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        if (null == connectivityManager)
            return netType;

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NetworkType.WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NetworkType.NETWORK_2G;
                        break;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NetworkType.NETWORK_3G;
                        break;
                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NetworkType.NETWORK_4G;
                        break;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if ("TD-SCDMA".equalsIgnoreCase(subtypeName) || "WCDMA".equalsIgnoreCase(
                                subtypeName) || "CDMA2000".equalsIgnoreCase(subtypeName)) {
                            netType = NetworkType.NETWORK_3G;
                        } else {
                            netType = NetworkType.UNKNOWN;
                        }
                        break;
                }
            } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                netType = NetworkType.ETHERNET;
            } else {
                netType = NetworkType.UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * 判断网络连接是否可用
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected() && networkinfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public static List<NetWork> getNetworks() {
        List<NetWork> networkItemList = new ArrayList<>();
        Map<String, NetWork> netFaces = getAllMacAddress();
        if (!netFaces.isEmpty()) {
            for (int i = 0; i < 10; ++i) {
                if (netFaces.containsKey("eth" + i)) {
                    networkItemList.add(netFaces.get("eth" + i));
                    break;
                }
            }

            for (int i = 0; i < 10; ++i) {
                if (netFaces.containsKey("wlan" + i)) {
                    networkItemList.add(netFaces.get("wlan" + i));
                    break;
                }
            }

            for (int i = 0; i < 10; ++i) {
                if (netFaces.containsKey("ppp" + i)) {
                    networkItemList.add(netFaces.get("ppp" + i));
                    break;
                }
            }

            for (int i = 0; i < 10; i++) {
                if (netFaces.containsKey("tun" + i)) {
                    networkItemList.add(netFaces.get("tun" + i));
                    break;
                }
            }
        }
        return networkItemList;
    }

    public static Map<String, NetWork> getAllMacAddress() {
        Map<String, NetWork> netFaces = new HashMap<>(4);
        try {
            //NetworkInterface.getNetworkInterfaces()需要 INTERNET 权限
            Enumeration<NetworkInterface> networkInterfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nFace = networkInterfaces.nextElement();
                String name = nFace.getName();

                /**
                 * <p>tun0 vpn hardware address, ppp0 for mobile network</p>
                 */
                if (name.contains("tun") || name.contains("ppp")) {
                    List<InterfaceAddress> interfaceAddressList = nFace.getInterfaceAddresses();
                    NetWork networkItem = new NetWork();

                    for (InterfaceAddress address : interfaceAddressList) {
                        String ip = address.getAddress().getHostAddress();
                        if (null == ip || ip.length() == 0) {
                            continue;
                        }
                        if (ip.contains(".")) {
                            networkItem.setIpv4(ip);
                            networkItem.setIsEnable(true);
                        } else {
                            int index = ip.length();
                            if (ip.contains("%")) {
                                index = ip.indexOf("%");
                            }
                            networkItem.setIpv6(ip.substring(0, index));
                            networkItem.setIsEnable(false);
                        }
                    }
                    networkItem.setType(getDevType(name).getValue());
                    netFaces.put(name, networkItem);
                }

                byte[] data = nFace.getHardwareAddress();
                if (null == data || data.length < 1) {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (byte bt : data) {
                    stringBuilder.append(String.format("%02x:", bt));
                }

                NetWork networkItem = new NetWork();
                networkItem.setMac(stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                                                .toString()
                                                .toUpperCase());
                List<InterfaceAddress> interfaceAddressList = nFace.getInterfaceAddresses();
                if (!interfaceAddressList.isEmpty()) {
                    for (InterfaceAddress interfaceAddress : interfaceAddressList) {
                        //获取ip字符串
                        String ip = interfaceAddress.getAddress().getHostAddress();
                        if (null != ip && ip.length() > 0) {
                            if (ip.contains(".")) {
                                networkItem.setIpv4(ip);
                                networkItem.setIsEnable(true);
                            } else {
                                int index = ip.length();
                                if (ip.contains("%")) {
                                    index = ip.indexOf("%");
                                }
                                networkItem.setIpv6(ip.substring(0, index));
                                networkItem.setIsEnable(false);
                            }
                        }
                        //获取广播ip字符串
                        InetAddress inetAddressBrct = interfaceAddress.getBroadcast();
                        if (inetAddressBrct != null) {
                            String broadCast = inetAddressBrct.getHostAddress();
                            if (null != broadCast && !"".equals(broadCast)) {
                                networkItem.setBroadCastIp(broadCast);
                            }
                        }

                    }
                }

                networkItem.setType(getDevType(name).getValue());
                netFaces.put(name, networkItem);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return netFaces;
    }

    private static CloudDevType getDevType(String name) {
        if ("wlan0".equalsIgnoreCase(name)) {
            return CloudDevType.WIFI;
        } else if ("ppp0".equalsIgnoreCase(name)) {
            return CloudDevType.MOBILE;
        } else if ("tun0".equalsIgnoreCase(name)) {
            return CloudDevType.VPN;
        } else if ("eth0".equalsIgnoreCase(name)) {
            return CloudDevType.ETHERNET;
        }
        return CloudDevType.UNKNOWN;
    }

    /**
     * @param operator
     * @param dbm
     * @return
     */
    public static int getMobileSignalStrength(CloudCarrierOperator operator, int dbm) {
        int level = 0;
        if (operator == CloudCarrierOperator.CUCC) {
            if (dbm >= -75) {
                level = SIGNAL_STRENGTH_GREAT;
            } else if (dbm >= -85) {
                level = SIGNAL_STRENGTH_GOOD;
            } else if (dbm >= -95) {
                level = SIGNAL_STRENGTH_MODERATE;
            } else if (dbm >= -100) {
                level = SIGNAL_STRENGTH_POOR;
            } else {
                level = SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
            }
        } else if (operator == CloudCarrierOperator.CTCC) {
            if (dbm >= -65) {
                level = SIGNAL_STRENGTH_GREAT;
            } else if (dbm >= -75) {
                level = SIGNAL_STRENGTH_GOOD;
            } else if (dbm >= -90) {
                level = SIGNAL_STRENGTH_MODERATE;
            } else if (dbm >= -105) {
                level = SIGNAL_STRENGTH_POOR;
            } else {
                level = SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
            }
        } else if (operator == CloudCarrierOperator.CMCC) {
            if (dbm >= -75) {
                level = SIGNAL_STRENGTH_GREAT;
            } else if (dbm >= -85) {
                level = SIGNAL_STRENGTH_GOOD;
            } else if (dbm >= -95) {
                level = SIGNAL_STRENGTH_MODERATE;
            } else if (dbm >= -100) {
                level = SIGNAL_STRENGTH_POOR;
            } else {
                level = SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
            }
        }
        return level;
    }

    public static int getWifiSignalStrength(Context context, int levels) {
        int MIN_RSSI = -100;
        int MAX_RSSI = -55;
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                                                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        int rssi = info.getRssi();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return WifiManager.calculateSignalLevel(info.getRssi(), levels);
        } else {
            // this is the code since 4.0.1
            if (rssi <= MIN_RSSI) {
                return 0;
            } else if (rssi >= MAX_RSSI) {
                return levels - 1;
            } else {
                float inputRange = (MAX_RSSI - MIN_RSSI);
                float outputRange = (levels - 1);
                return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
            }
        }
    }

    public static long getMobileRxBytes() {
        return TrafficStats.getMobileRxBytes() == TrafficStats.UNSUPPORTED ? 0 :
                TrafficStats.getMobileRxBytes();
    }

    public static long getMobileTxBytes() {
        return TrafficStats.getMobileTxBytes() == TrafficStats.UNSUPPORTED ? 0 :
                TrafficStats.getMobileTxBytes();
    }

    public static long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes() == TrafficStats.UNSUPPORTED ? 0 :
                TrafficStats.getTotalRxBytes();
    }

    public static long getTotalTxBytes() {
        return TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ? 0 :
                TrafficStats.getTotalTxBytes();
    }

    /**
     * <p>获得运营商</p>
     * CMCC: 中国移动 46000
     * CUCC: 中国联通 46001
     * CTCC: 中国电信 46003
     */
    public static CloudCarrierOperator getCarrierOperator(TelephonyManager telephonyManager) {
        CloudCarrierOperator name = CloudCarrierOperator.UNKNOWN;
        String strNetworkOperator = telephonyManager.getNetworkOperator();
        if (strNetworkOperator.equals(CloudCarrierOperator.CMCC.getValue())) {
            name = CloudCarrierOperator.CMCC;
        } else if (strNetworkOperator.equals(CloudCarrierOperator.CUCC.getValue())) {
            name = CloudCarrierOperator.CUCC;
        } else if (strNetworkOperator.equals(CloudCarrierOperator.CTCC.getValue())) {
            name = CloudCarrierOperator.CTCC;
        }
        return name;
    }
}
