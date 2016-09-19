package pw.tdekk.rs.hook;

/**
 * Created by timde on 7/2/2016.
 */
public class Hook {
    String alias;
    String owner;
    String name;
    String desc;
    int type = Type.FIELD;

    public Hook(String alias, String owner, String name, String desc, int type) {
        this.alias = alias;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

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

    public String toString() {
        return String.format("%s %s = %s.%s", internalize(desc), alias, owner, name);
    }

    private String internalize(String desc) {
        switch (desc) {
            case "B":
                return "byte";
            case "I":
                return "int";
            case "J":
                return "long";
            case "D":
                return "double";
            case "F":
                return "float";
            case "Z":
                return "boolean";
            case "S":
                return "short";
            default: {
                if (desc.contains("."))
                    desc = desc.substring(desc.lastIndexOf('.'));
                return desc;
            }
        }
    }
}
