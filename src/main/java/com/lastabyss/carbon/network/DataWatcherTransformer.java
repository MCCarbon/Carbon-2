package com.lastabyss.carbon.network;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.IOException;

import com.lastabyss.carbon.network.watchedentity.WatchedEntity;
import com.lastabyss.carbon.network.watchedentity.SpecificType.RemappingEntry;
import com.lastabyss.carbon.utils.DataWatcherSerializer;
import com.lastabyss.carbon.utils.DataWatcherSerializer.DataWatcherObject;

public class DataWatcherTransformer {

    public static byte[] transform(WatchedEntity entity, byte[] data) throws IOException {
        if (entity == null) {
            return data;
        }
        TIntObjectMap<DataWatcherObject> objects = DataWatcherSerializer.decodeData(data);
        TIntObjectMap<DataWatcherObject> newobjects = new TIntObjectHashMap<DataWatcherObject>();
        //copy entity
        moveDWData(objects, newobjects, 0, 0); //flags
        moveDWData(objects, newobjects, 1, 1); //air
        moveDWData(objects, newobjects, 2, 2); //nametag
        moveDWData(objects, newobjects, 3, 3); //nametagvisible
        //copy specific types
        for (RemappingEntry entry : entity.getType().getRemaps()) {
            moveDWData(objects, newobjects, entry.getFrom(), entry.getTo());
        }
        return DataWatcherSerializer.encodeData(newobjects);
    }

    private static void moveDWData(TIntObjectMap<DataWatcherObject> from, TIntObjectMap<DataWatcherObject> to, int oldId, int newId) {
        DataWatcherObject dwobject = from.remove(oldId);
        if (dwobject != null) {
            to.put(newId, dwobject);
        }
    }

}
