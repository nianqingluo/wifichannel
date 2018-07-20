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

import java.util.Arrays;
import java.util.List;

public class WiFiChannelsGHZ5 extends WiFiChannels {
    public static final Pair<WiFiChannel, WiFiChannel> SET1 = new Pair<>(new WiFiChannel(36, 5180), new WiFiChannel(64, 5320));
    public static final Pair<WiFiChannel, WiFiChannel> SET2 = new Pair<>(new WiFiChannel(100, 5500), new WiFiChannel(144, 5720));
    public static final Pair<WiFiChannel, WiFiChannel> SET3 = new Pair<>(new WiFiChannel(149, 5745), new WiFiChannel(165, 5825));
    public static final List<Pair<WiFiChannel, WiFiChannel>> SETS = Arrays.asList(SET1, SET2, SET3);
    private static final Pair<Integer, Integer> RANGE = new Pair<>(4900, 5899);

    WiFiChannelsGHZ5() {
        super(RANGE, SETS);
    }

    @Override
    @NonNull
    public List<WiFiChannel> getAvailableChannels(@NonNull String countryCode) {
        return getAvailableChannels(WiFiChannelCountry.get(countryCode).getChannelsGHZ5());
    }

}
