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
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.collections4.Predicate;

import java.util.Collections;
import java.util.List;

class ChannelRatingAdapter {
    private static final int MAX_CHANNELS_TO_DISPLAY = 10;

    private TextView bestChannels;
    private ChannelRating channelRating;
    private Context mContext;
    private Cache cache;
    private Transformer transformer;

    ChannelRatingAdapter(@NonNull Context context, @NonNull TextView bestChannels) {
        //        super(context, R.layout.channel_rating_details, new ArrayList<WiFiChannel>());
        this.bestChannels = bestChannels;
        setChannelRating(new ChannelRating());
        this.cache = new Cache();
        mContext = context;
        this.transformer = new Transformer();
    }

    void setChannelRating(@NonNull ChannelRating channelRating) {
        this.channelRating = channelRating;
    }

    public void update() {
        WiFiData wiFiData = null;
        List<ScanResult> scanResults = Collections.emptyList();
        WifiInfo wifiInfo = null;
        List<WifiConfiguration> configuredNetworks = null;
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            if (wifiManager.startScan()) {
                scanResults = wifiManager.getScanResults();
            }
            wifiInfo = wifiManager.getConnectionInfo();
            configuredNetworks = wifiManager.getConfiguredNetworks();
        } catch (Exception e) {
            // critical error: set to no results and do not die
        }
        cache.add(scanResults);
        wiFiData = transformer.transformToWiFiData(cache.getScanResults(), wifiInfo, configuredNetworks);

        WiFiBand wiFiBand = WiFiBand.GHZ5;
        //        List<WiFiChannel> wiFiChannels = setWiFiChannels(wiFiBand);
        List<WiFiChannel> wiFiChannels = setWiFiChannels(wiFiBand);

        Predicate<WiFiDetail> predicate = new WiFiBandPredicate(wiFiBand);
        List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(predicate, SortBy.STRENGTH);
        channelRating.setWiFiDetails(wiFiDetails);
        bestChannels(wiFiBand, wiFiChannels);
    }

    @NonNull
    private List<WiFiChannel> setWiFiChannels(WiFiBand wiFiBand) {
        String countryCode = "CN";
        List<WiFiChannel> wiFiChannels = wiFiBand.getWiFiChannels().getAvailableChannels(countryCode);
        return wiFiChannels;
    }


    void bestChannels(@NonNull WiFiBand wiFiBand, @NonNull List<WiFiChannel> wiFiChannels) {
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
            //            bestChannels.setText(result.toString());
            //            bestChannels.setTextColor(ContextCompat.getColor(getContext(), R.color.success_color));
            Toast.makeText(mContext, result.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "没有最优的信道", Toast.LENGTH_SHORT).show();
            //            Resources resources = getContext().getResources();
            //            StringBuilder message = new StringBuilder(resources.getText(R.string.channel_rating_best_none));
            //            if (WiFiBand.GHZ2.equals(wiFiBand)) {
            //                message.append(resources.getText(R.string.channel_rating_best_alternative));
            //                message.append(" ");
            //                message.append(getContext().getResources().getString(WiFiBand.GHZ5.getTextResource()));
            //            }
            //            bestChannels.setText(message);
            //            bestChannels.setTextColor(ContextCompat.getColor(getContext(), R.color.error_color));
        }
    }

}
