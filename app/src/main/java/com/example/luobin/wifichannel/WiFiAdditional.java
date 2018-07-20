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

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiAdditional {
    public static final WiFiAdditional EMPTY = new WiFiAdditional();

    private final WiFiConnection wiFiConnection;

    public WiFiAdditional(@NonNull WiFiConnection wiFiConnection) {
        this.wiFiConnection = wiFiConnection;
    }

    public WiFiAdditional() {
        this(WiFiConnection.EMPTY);
    }

    @NonNull
    public WiFiConnection getWiFiConnection() {
        return wiFiConnection;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}