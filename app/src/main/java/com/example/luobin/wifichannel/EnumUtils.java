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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnumUtils {

    private EnumUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static <T extends Enum> T find(@NonNull Class<T> enumType, int index, @NonNull T defaultValue) {
        T[] values = enumType.getEnumConstants();
        if (index < 0 || index >= values.length) {
            return defaultValue;
        }
        return values[index];
    }

    @NonNull
    public static <T extends Enum> T find(@NonNull Class<T> enumType, @NonNull Predicate<T> predicate, @NonNull T defaultValue) {
        List<T> results = new ArrayList<>(CollectionUtils.select(values(enumType), predicate));
        return results.isEmpty() ? defaultValue : results.get(0);
    }


    @NonNull
    public static <T extends Enum> Set<T> values(@NonNull Class<T> enumType) {
        return new HashSet<>(Arrays.asList(enumType.getEnumConstants()));
    }
}