package net.artemisia.dev.api.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {
    private final String head;
    private final List<TreeUtils> children = new ArrayList<>();

    public TreeUtils(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public List<TreeUtils> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(head).append("\n");
        buildTreeString(sb, "", true);
        return sb.toString();
    }

    private void buildTreeString(StringBuilder sb, String prefix, boolean isTail) {
        sb.append(prefix).append(isTail ? "└─ " : "├─ ").append(head).append("\n");
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).buildTreeString(sb, prefix + (isTail ? "    " : "│   "), false);
        }
        if (!children.isEmpty()) {
            children.get(children.size() - 1).buildTreeString(sb, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}