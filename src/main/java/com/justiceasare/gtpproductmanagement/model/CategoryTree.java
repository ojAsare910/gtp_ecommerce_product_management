package com.justiceasare.gtpproductmanagement.model;

import org.springframework.stereotype.Component;

@Component
public class CategoryTree {
    private CategoryNode root;

    public CategoryTree() {
        this.root = null;
    }

    public void insert(Long id, String name) {
        root = insertRec(root, id, name);
    }

    private CategoryNode insertRec(CategoryNode root, Long id, String name) {
        if (root == null) {
            root = new CategoryNode(id, name);
            return root;
        }

        if (name.compareTo(root.getName()) < 0)
            root.setLeft(insertRec(root.getLeft(), id, name));
        else if (name.compareTo(root.getName()) > 0)
            root.setRight(insertRec(root.getRight(), id, name));

        return root;
    }

    public CategoryNode search(String name) {
        return searchRec(root, name);
    }

    private CategoryNode searchRec(CategoryNode root, String name) {
        if (root == null || root.getName().equals(name))
            return root;

        if (name.compareTo(root.getName()) < 0)
            return searchRec(root.getLeft(), name);

        return searchRec(root.getRight(), name);
    }

    public void delete(String name) {
        root = deleteRec(root, name);
    }

    private CategoryNode deleteRec(CategoryNode root, String name) {
        if (root == null)
            return root;

        if (name.compareTo(root.getName()) < 0)
            root.setLeft(deleteRec(root.getLeft(), name));
        else if (name.compareTo(root.getName()) > 0)
            root.setRight(deleteRec(root.getRight(), name));
        else {
            if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();

            root.setName(minValue(root.getRight()));
            root.setRight(deleteRec(root.getRight(), root.getName()));
        }

        return root;
    }

    private String minValue(CategoryNode root) {
        String minv = root.getName();
        while (root.getLeft() != null) {
            minv = root.getLeft().getName();
            root = root.getLeft();
        }
        return minv;
    }
}
