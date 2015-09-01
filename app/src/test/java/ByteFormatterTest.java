import android.provider.BaseColumns;

import com.slickmobile.simpledatausage.Utils;

import org.junit.Test;

public class ByteFormatterTest {

    // General database information
    public static String DATABASE_NAME = "usage.db";
    private static int DATABASE_VERSION = 1;
    public static String COL_ID = BaseColumns._ID;

    // Total usage table information
    public static String TOTAL_USAGE_TABLE = "total_usage";
    public static String COL_BYTES = "bytes";
    public static String[] TOTAL_USAGE_COLUMNS = { COL_ID, COL_BYTES };

    // App usage table information
    public static String APP_USAGE_TABLE = "app_usage";
    public static String COL_UID = "uid";
    public static String COL_PACKAGE = "package";


    @Test
    public void byteFormatterTest() {
        System.out.println("CREATE TABLE " + APP_USAGE_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_UID + " INTEGER,"
                + COL_PACKAGE + "TEXT,"
                + COL_BYTES + " INTEGER,"
                + "UNIQUE (" + COL_UID + ", " + COL_PACKAGE + ")"
                + ");");
    }
}
