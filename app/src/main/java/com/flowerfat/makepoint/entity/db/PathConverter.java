package com.flowerfat.makepoint.entity.db;

import android.graphics.Path;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.converter.TypeConverter;

/**
 * Created by bigflower on 2016/4/10.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class PathConverter extends TypeConverter<String, Path> {
    @Override
    public String getDBValue(Path model) {
        return new Gson().toJson(model);
    }

    @Override
    public Path getModelValue(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        } else {
            Path path = new Gson().fromJson(data, Path.class);
            return path;
        }
    }
}
