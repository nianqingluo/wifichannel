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

public enum Strength {
    ZERO(),
    ONE(),
    TWO(),
    THREE(),
    FOUR();


    Strength() {
    }

    @NonNull
    public static Strength calculate(int level) {
        int index = WiFiUtils.calculateSignalLevel(level, values().length);
        return Strength.values()[index];
    }

}
