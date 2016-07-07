package pw.tdekk.rs.hook;

/**
 * Created by timde on 7/2/2016.
 */
public class Hook {
    String owner;
    String name;
    String desc;
    int type = Type.FIELD;

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }

    public static class Type {
        public static final int FIELD = 0;
        public static final int METHOD = 1;
    }
}
