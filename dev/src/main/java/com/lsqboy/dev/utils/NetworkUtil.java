package com.lsqboy.dev.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.WorkerThread;

/**
 * Network Util
 * <p>
 * Created by lsqboy on 16-12-14.
 * Email：g.lsqboy@gmail.com
 * Copyright (c) 2016,lsqboy.com All Rights Reserved.
 */
public final class NetworkUtil {

    private NetworkUtil() {
        throw new IllegalAccessError();
    }

    /**
     * Check network connectivity.
     * <p>This method requires the caller to hold the permission
     * {@link Manifest.permission#ACCESS_NETWORK_STATE}.
     * @param context to use to check for network connectivity.
     * @return true if connected, false otherwise.
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET})
    public static boolean isOnline(@NonNull Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Returns the address of a host according to the given host string name host.
     * The host string may be either a machine name or a dotted string IP address.
     * If the latter, the hostName field is determined upon demand.
     * host can be null which means that an address of the loopback interface is returned.
     * @param host he hostName to be resolved to an address or null.
     * @return address of the host
     */
    @Nullable
    @WorkerThread
    @RequiresPermission(Manifest.permission.INTERNET)
    public static String resolveDomainName(@Nullable String host) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return null;
    }

    /**
     * Get local IPv4 internet address
     * @return Local IPv4 internet address
     */
    @Nullable
    public static String getLocalHostIpv4Address() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress inetAddres : localInetAddress) {
            if (inetAddres instanceof Inet4Address) {
                return inetAddres.getHostAddress();
            }
        }
        return null;
    }

    /**
     * Get local IPv6 internet address
     * @return Local IPv6 internet address
     */
    @Nullable
    public static String getLocalHostIpv6Address() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress inetAddres : localInetAddress) {
            if (inetAddres instanceof Inet6Address) {
                return inetAddres.getHostAddress();
            }
        }
        return null;
    }

    /**
     * Get local internet address
     * @return Local internet address
     */
    @NonNull
    public static List<InetAddress> getLocalHostAddress() {
        List<InetAddress> inetAddressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && !inetAddress.isAnyLocalAddress()) {
                        inetAddressList.add(inetAddress);
                    }
                }
            }
        } catch (SocketException ignored) {
        }
        return inetAddressList;
    }

    /**
     * Detects whether or not IPv6 is supported
     * @return true - IPv6 supported
     */
    public static boolean isIpv6Supported() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress localInetAddres : localInetAddress) {
            if (localInetAddres instanceof Inet6Address) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the wifi network interface is connected.
     * @param context Context
     * @return true - the wifi network interface is connected.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(Context context) {
        NetworkInfo[] allNetworkInfo = getAllNetworkInfo(context);
        if (allNetworkInfo != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether the ethernet network interface is connected.
     * @param context Context
     * @return true - the wifi network interface is connected.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isEthernetConnected(Context context) {
        NetworkInfo[] allNetworkInfo = getAllNetworkInfo(context);
        if (allNetworkInfo != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET && networkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether the mobile network interface is connected.
     * @param context Context
     * @return true - the wifi network interface is connected.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileConnected(Context context) {
        NetworkInfo[] allNetworkInfo = getAllNetworkInfo(context);
        if (allNetworkInfo != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns connection status information about all network types supported by the device.
     * @param context Context
     * @return an array of {@link NetworkInfo} objects.  Check each
     * {@link NetworkInfo#getType} for which type each applies.
     */
    @SuppressWarnings("deprecation")
    @Nullable
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo[] getAllNetworkInfo(final Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return connMgr.getAllNetworkInfo();
            } else {
                Network[] networks = connMgr.getAllNetworks();
                if (networks != null) {
                    NetworkInfo[] networkInfos = new NetworkInfo[networks.length];
                    for (int i = 0; i < networks.length; i++) {
                        networkInfos[i] = connMgr.getNetworkInfo(networks[i]);
                    }
                    return networkInfos;
                }
            }
        }
        return null;
    }


    /**
     * Reports the type of active network
     * @param context Context
     * @return the type of network.
     */
    public static int getActiveNetworkType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("ConstantConditions")
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        //noinspection ResourceType
        return activeNetworkInfo.getType();
    }
}
