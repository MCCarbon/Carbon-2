package com.lastabyss.carbon.network;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.IOException;

import com.lastabyss.carbon.network.watchedentity.WatchedEntity;
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
        //copy living entity
        if (entity.isLiving()) {
            moveDWData(objects, newobjects, 2, 2); //nametag
            moveDWData(objects, newobjects, 3, 3); //nametagvisible
            moveDWData(objects, newobjects, 6, 6); //health
            moveDWData(objects, newobjects, 7, 7); //potion color
            moveDWData(objects, newobjects, 8, 8); //potion ambient
            moveDWData(objects, newobjects, 9, 9); //arrows
            moveDWData(objects, newobjects, 15, 10); //no-ai
        }
        //copy ageable
        if (entity.isAgeable()) {
            moveDWData(objects, newobjects, 12, 11); //age
        }
        //copy tameable
        if (entity.isTameable()) {
            moveDWData(objects, newobjects, 16, 12); //tame flags
            moveDWData(objects, newobjects, 17, 13); //owner
        }
        //copy player
        if (entity.isPlayer()) {
            moveDWData(objects, newobjects, 17, 10); //absorption hearts
            moveDWData(objects, newobjects, 18, 11); //score
            moveDWData(objects, newobjects, 10, 12); //skinflags
        }
        //copy specific types
        switch (entity.getType()) {
            case VILLAGER: {
                moveDWData(objects, newobjects, 16, 12); //profession
                break;
            }
            case HORSE: {
                moveDWData(objects, newobjects, 16, 12); //info flags
                moveDWData(objects, newobjects, 19, 13); //type
                moveDWData(objects, newobjects, 20, 14); //color/variant
                moveDWData(objects, newobjects, 21, 15); //owner
                moveDWData(objects, newobjects, 22, 16); //armor
                break;
            } case ITEM_FRAME: {
            	moveDWData(objects, newobjects, 8, 5); //item
            	moveDWData(objects, newobjects, 9, 6); //rotation
            	break;
            } case ITEM: {
            	moveDWData(objects, newobjects, 10, 5); //item
            	break;
            }
            case BAT: {
                moveDWData(objects, newobjects, 16, 11);
                break;
            }
            default: {
                break;
            }
        }
        //TODO: more types
        return DataWatcherSerializer.encodeData(newobjects);
    }

    private static void moveDWData(TIntObjectMap<DataWatcherObject> from, TIntObjectMap<DataWatcherObject> to, int oldId, int newId) {
        DataWatcherObject dwobject = from.remove(oldId);
        if (dwobject != null) {
            to.put(newId, dwobject);
        }
    }

}
