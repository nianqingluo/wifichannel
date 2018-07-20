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

import java.util.Comparator;

public enum GroupBy {
    NONE(new None(), new None());

    private final Comparator<WiFiDetail> sortOrderComparator;
    private final Comparator<WiFiDetail> groupByComparator;

    GroupBy(@NonNull Comparator<WiFiDetail> sortOrderComparator, @NonNull Comparator<WiFiDetail> groupByComparator) {
        this.sortOrderComparator = sortOrderComparator;
        this.groupByComparator = groupByComparator;
    }

    @NonNull
    Comparator<WiFiDetail> sortOrderComparator() {
        return sortOrderComparator;
    }

    @NonNull
    Comparator<WiFiDetail> groupByComparator() {
        return groupByComparator;
    }

    static class None implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return lhs.equals(rhs) ? 0 : 1;
        }
    }

}
