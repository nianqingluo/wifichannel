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

public class Configuration {
    public static final int SIZE_MAX = 4096;

    private int size;

    public Configuration() {
        setSize(SIZE_MAX);
    }

    public boolean isSizeAvailable() {
        return size == SIZE_MAX;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
