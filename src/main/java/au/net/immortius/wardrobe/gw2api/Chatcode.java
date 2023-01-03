package au.net.immortius.wardrobe.gw2api;

import com.google.common.primitives.UnsignedBytes;

import java.util.Base64;

/**
 * Handling of GW2's chat link format. Can both generate and extract information from chat codes.
 * Somewhat tailored for the little required for generating the wardrobe site.
 */
public final class Chatcode {

    private static final int ITEM_TYPE = 2;

    private Chatcode() {
    }

    /**
     * Created a chat code for the given link type and id.
     * @param type The type code for the chat link
     * @param id The id of the link
     * @return The requested chat code
     */
    public static String create(int type, String id) {
        return create(type, id, 1);
    }

    /**
     * Created a chat code for the given link type and id.
     * @param type The type code for the chat link
     * @param id The id of the link
     * @param quantity The quantity of the item to link (ignored for types that don't support quantity)
     * @return The requested chat code
     */
    public static String create(int type, String id, int quantity) {
        int idValue = Integer.parseInt(id);
        if (type == ITEM_TYPE) {
            byte[] info = new byte[6];
            info[0] = (byte) type;
            info[1] = (byte) quantity;
            info[2] = (byte) (idValue & 0xFF);
            info[3] = (byte) ((idValue >> 8) & 0xFF);
            info[4] = (byte) ((idValue >> 16) & 0xFF);
            info[5] = 0;
            return "[&" + Base64.getEncoder().encodeToString(info) + "]";
        } else {
            byte[] info = new byte[5];
            info[0] = (byte) type;
            info[1] = (byte) (idValue & 0xFF);
            info[2] = (byte) ((idValue >> 8) & 0xFF);
            info[3] = (byte) ((idValue >> 16) & 0xFF);
            info[4] = 0;
            return "[&" + Base64.getEncoder().encodeToString(info) + "]";
        }
    }

    /**
     * @param chatcode The chat code to extract the item id from
     * @return The id from the chatcode
     * @throws IllegalArgumentException If the chat code is not valid.
     */
    public static String getId(String chatcode) {
        byte[] decoded = Base64.getDecoder().decode(chatcode.substring(2, chatcode.length() - 1));
        if (decoded[0] == ITEM_TYPE) {
            return Integer.toString((UnsignedBytes.toInt(decoded[2])) | (UnsignedBytes.toInt(decoded[3]) << 8) | (UnsignedBytes.toInt(decoded[4]) << 16));
        } else{
            return Integer.toString((UnsignedBytes.toInt(decoded[1])) | (UnsignedBytes.toInt(decoded[2]) << 8) | (UnsignedBytes.toInt(decoded[3]) << 16));
        }
    }
}
