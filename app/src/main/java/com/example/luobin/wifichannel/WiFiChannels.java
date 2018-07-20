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

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public abstract class WiFiChannels {
    public static final int FREQUENCY_SPREAD = 5;

    private final Pair<Integer, Integer> wiFiRange;
    private final List<Pair<WiFiChannel, WiFiChannel>> wiFiChannelPairs;

    WiFiChannels(@NonNull Pair<Integer, Integer> wiFiRange, @NonNull List<Pair<WiFiChannel, WiFiChannel>> wiFiChannelPairs) {
        this.wiFiRange = wiFiRange;
        this.wiFiChannelPairs = wiFiChannelPairs;
    }

    public boolean isInRange(int frequency) {
        return frequency >= wiFiRange.first && frequency <= wiFiRange.second;
    }

    @NonNull
    public WiFiChannel getWiFiChannelByFrequency(int frequency) {
        Pair<WiFiChannel, WiFiChannel> found = null;
        if (isInRange(frequency)) {
            found = IterableUtils.find(wiFiChannelPairs, new FrequencyPredicate(frequency));
        }
        return found == null ? WiFiChannel.UNKNOWN : getWiFiChannel(frequency, found);
    }

    @NonNull
    WiFiChannel getWiFiChannelByChannel(int channel) {
        Pair<WiFiChannel, WiFiChannel> found = IterableUtils.find(wiFiChannelPairs, new ChannelPredicate(channel));
        return found == null
                ? WiFiChannel.UNKNOWN
                : new WiFiChannel(channel, found.first.getFrequency() + ((channel - found.first.getChannel()) * FREQUENCY_SPREAD));
    }


    @NonNull
    WiFiChannel getWiFiChannel(int frequency, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        WiFiChannel first = wiFiChannelPair.first;
        WiFiChannel last = wiFiChannelPair.second;
        int channel = (int) (((double) (frequency - first.getFrequency()) / FREQUENCY_SPREAD) + first.getChannel() + 0.5);
        if (channel >= first.getChannel() && channel <= last.getChannel()) {
            return new WiFiChannel(channel, frequency);
        }
        return WiFiChannel.UNKNOWN;
    }

    @NonNull
    public abstract List<WiFiChannel> getAvailableChannels(String countryCode);

    @NonNull
    List<WiFiChannel> getAvailableChannels(SortedSet<Integer> channels) {
        return new ArrayList<>(CollectionUtils.collect(channels, new ToWiFiChannel(this)));
    }

    private static class ToWiFiChannel implements org.apache.commons.collections4.Transformer<Integer, WiFiChannel> {
        private final WiFiChannels wiFiChannels;

        private ToWiFiChannel(@NonNull WiFiChannels wiFiChannels) {
            this.wiFiChannels = wiFiChannels;
        }

        @Override
        public WiFiChannel transform(Integer input) {
            return wiFiChannels.getWiFiChannelByChannel(input);
        }
    }

    private class FrequencyPredicate implements Predicate<Pair<WiFiChannel, WiFiChannel>> {
        private final int frequency;

        private FrequencyPredicate(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public boolean evaluate(Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            return !WiFiChannel.UNKNOWN.equals(getWiFiChannel(frequency, wiFiChannelPair));
        }
    }

    private class ChannelPredicate implements Predicate<Pair<WiFiChannel, WiFiChannel>> {
        private final int channel;

        private ChannelPredicate(int channel) {
            this.channel = channel;
        }

        @Override
        public boolean evaluate(Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            return channel >= wiFiChannelPair.first.getChannel() && channel <= wiFiChannelPair.second.getChannel();
        }
    }


}