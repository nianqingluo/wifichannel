/*
 * WiFiAnalyzer
 * Copyright (C) 2018  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.example.luobin.wifichannel;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.apache.commons.collections4.Predicate;

import java.util.Collections;
import java.util.List;

class ChannelRatingAdapter {
    private static final int MAX_CHANNELS_TO_DISPLAY = 10;

    private ChannelRating channelRating;
    private Context mContext;
    private Cache cache;
    private Transformer transformer;
    private WifiManager mWifiManager;

    ChannelRatingAdapter(@NonNull Context context) {
        setChannelRating(new ChannelRating());
        this.cache = new Cache();
        mContext = context;
        this.transformer = new Transformer();
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    void setChannelRating(@NonNull ChannelRating channelRating) {
        this.channelRating = channelRating;
    }

    public void update(WiFiBand type) {
        List<ScanResult> scanResults = Collections.emptyList();
        WifiInfo wifiInfo = null;
        try {
            if (!mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(true);
            }
            if (mWifiManager.startScan()) {
                scanResults = mWifiManager.getScanResults();
            }
            wifiInfo = mWifiManager.getConnectionInfo();
        } catch (Exception e) {
            // critical error: set to no results and do not die
        }
        cache.add(scanResults);
        WiFiData wiFiData = transformer.transformToWiFiData(cache.getScanResults(), wifiInfo);

        WiFiBand wiFiBand = type;
        //        List<WiFiChannel> wiFiChannels = setWiFiChannels(wiFiBand);
        List<WiFiChannel> wiFiChannels = setWiFiChannels(wiFiBand);

        Predicate<WiFiDetail> predicate = new WiFiBandPredicate(wiFiBand);
        List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(predicate, SortBy.STRENGTH);
        channelRating.setWiFiDetails(wiFiDetails);
        bestChannels(wiFiChannels);
    }

    @NonNull
    private List<WiFiChannel> setWiFiChannels(WiFiBand wiFiBand) {
        String countryCode = "CN";
        List<WiFiChannel> wiFiChannels = wiFiBand.getWiFiChannels().getAvailableChannels(countryCode);
        return wiFiChannels;
    }


    void bestChannels(@NonNull List<WiFiChannel> wiFiChannels) {
        List<ChannelAPCount> channelAPCounts = channelRating.getBestChannels(wiFiChannels);
        int channelCount = 0;
        StringBuilder result = new StringBuilder();
        for (ChannelAPCount channelAPCount : channelAPCounts) {
            if (channelCount > MAX_CHANNELS_TO_DISPLAY) {
                result.append("...");
                break;
            }
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(channelAPCount.getWiFiChannel().getChannel());
            channelCount++;
        }
        if (result.length() > 0) {
            Toast.makeText(mContext, result.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "没有最优的信道", Toast.LENGTH_SHORT).show();
        }
    }

}
