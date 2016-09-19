package pw.tdekk.rs.hook;

import pw.tdekk.rs.AbstractIdentifier;

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
        return String.format("  +  %s %s = %s.%s", internalize(desc), alias, owner, name);
    }

    private String internalize(String desc) {
        int depth = desc.length() - (desc = desc.replace("[", "")).length();
        switch (desc) {
            case "B":
                return "byte" + array(depth, "");
            case "I":
                return "int" + array(depth, "");
            case "J":
                return "long" + array(depth, "");
            case "D":
                return "double" + array(depth, "");
            case "F":
                return "float" + array(depth, "");
            case "Z":
                return "boolean" + array(depth, "");
            case "S":
                return "short" + array(depth, "");
            default: {
                if (desc.contains("/")) {
                    desc = desc.substring(desc.lastIndexOf('/') + 1, desc.length() - 1);
                } else if (desc.length() < 5) {
                    desc = AbstractIdentifier.externalDesc(desc.substring(1, desc.length() - 1));
                }
                return desc + array(depth, "");
            }
        }
    }

    private String array(int depth, String val) {
        if (depth == 0) return val;
        return array(depth - 1, val + "[]");
    }

    /**
     * Hooks are defined by their names! cannot have conflicting names!
     */
    public int hashCode() {
        return alias.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof Hook) {
            Hook hook = (Hook) o;
            if (hook.alias.equals(alias)) return true;
        }
        return false;
    }
}
