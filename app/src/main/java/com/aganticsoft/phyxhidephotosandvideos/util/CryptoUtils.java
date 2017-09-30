package com.aganticsoft.phyxhidephotosandvideos.util;

import com.snatik.storage.Storage;

/**
 * Created by ttson
 * Date: 9/27/2017.
 */

public class CryptoUtils {

    public static boolean saveFile(Storage storage, byte[] data, String path) {
        return storage.createFile(path, data);
    }

    public static byte[] readFile(Storage storage, String path) {
        return storage.readFile(path);
    }
}
