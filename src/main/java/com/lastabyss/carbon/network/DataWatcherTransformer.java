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
        // copy entity
        moveDWData(objects, newobjects, 0, 0); // flags
        moveDWData(objects, newobjects, 1, 1); // air
        moveDWData(objects, newobjects, 2, 2); // nametag
        moveDWData(objects, newobjects, 3, 3); // nametagvisible
        // copy living entity
        if (entity.isLiving()) {
            moveDWData(objects, newobjects, 6, 6); // health
            moveDWData(objects, newobjects, 7, 7); // potion color
            moveDWData(objects, newobjects, 8, 8); // potion ambient
            moveDWData(objects, newobjects, 9, 9); // arrows
            moveDWData(objects, newobjects, 15, 10); // no-ai
        }
        // copy ageable
        if (entity.isAgeable()) {
            moveDWData(objects, newobjects, 12, 11); // age
        }
        // copy tameable
        if (entity.isTameable()) {
            moveDWData(objects, newobjects, 16, 12); // tame flags
            moveDWData(objects, newobjects, 17, 13); // owner
        }
        // copy player
        if (entity.isPlayer()) {
            moveDWData(objects, newobjects, 17, 10); // absorption hearts
            moveDWData(objects, newobjects, 18, 11); // score
            moveDWData(objects, newobjects, 10, 12); // skinflags
        }
        // copy specific types
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
