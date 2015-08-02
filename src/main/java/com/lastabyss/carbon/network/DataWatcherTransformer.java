package com.lastabyss.carbon.network;

import gnu.trove.map.TIntObjectMap;

import java.io.IOException;

import com.lastabyss.carbon.utils.DataWatcherSerializer;
import com.lastabyss.carbon.utils.DataWatcherSerializer.DataWatcherObject;
import com.lastabyss.carbon.utils.watchedentity.WatchedEntity;

public class DataWatcherTransformer {

    public static byte[] transform(WatchedEntity entity, byte[] data) throws IOException {
        if (entity == null) {
            return data;
        }
        TIntObjectMap<DataWatcherObject> objects = DataWatcherSerializer.decodeData(data);
        if (entity.isPlayer()) {
            DataWatcherObject abshearts = objects.remove(17);
            DataWatcherObject score = objects.remove(18);
            DataWatcherObject skinflags = objects.remove(10);
            if (abshearts != null) {
                objects.put(10, abshearts);
            }
            if (score != null) {
                objects.put(11, score);
            }
            if (skinflags != null) {
                objects.put(12, skinflags);
            }
        }
        return DataWatcherSerializer.encodeData(objects);
    }

}
