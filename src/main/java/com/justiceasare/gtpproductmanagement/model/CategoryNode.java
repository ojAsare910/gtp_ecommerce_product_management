package com.justiceasare.gtpproductmanagement.model;

public class CategoryNode {
    private Long id;
    private String name;
    private CategoryNode left;
    private CategoryNode right;

    public CategoryNode(Long id, String name) {
        this.id = id;
        this.name = name;
        this.left = null;
        this.right = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryNode getLeft() {
        return left;
    }

    public void setLeft(CategoryNode left) {
        this.left = left;
    }

    public CategoryNode getRight() {
        return right;
    }

    public void setRight(CategoryNode right) {
        this.right = right;
    }
}