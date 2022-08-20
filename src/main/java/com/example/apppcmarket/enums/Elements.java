package com.example.apppcmarket.enums;

public enum Elements {

    CATEGORY("Kategoriya topilmadi", "Kategoriya qo'shildi",
            "Kategoriya active holatdamas", "Kategoriya mavjud",
            "Kategoriya o'zgartirildi","Kategoriya o'chirildi"),

    PRODUCT("Product topilmadi", "Product qo'shildi",
            "Product active holatdamas", "Product mavjud",
            "Product o'zgartirildi","Product o'chirildi"),

    ADDRESS("Address topilmadi", "Address qo'shildi",
            "Address active holatdamas", "Address mavjud",
            "Address o'zgartirildi","Address o'chirildi"),

    CUSTOMER("Mijoz topilmadi", "Mijoz qo'shildi",
            "Mijoz active holatdamas", "Mijoz mavjud",
            "Mijoz o'zgartirildi","Mijoz o'chirildi"),

    SUPPLIER("Ta'minotchi topilmadi", "Ta'minotchi qo'shildi",
            "Ta'minotchi active holatdamas", "Ta'minotchi mavjud",
            "Ta'minotchi o'zgartirildi","Ta'minotchi o'chirildi"),

    ORDER("Buyurtma topilmadi", "Buyurtma qo'shildi",
            "Buyurtma active holatdamas", "Buyurtma mavjud",
            "Buyurtma o'zgartirildi","Buyurtma o'chirildi");

    private final String elementNotFound;

    private final String elementAdded;

    private final String elementIsNotActive;

    private final String elementExists;

    private final String elementEdited;

    private final String elementDeleted;


    Elements(String elementNotFound, String elementAdded, String elementIsNotActive, String elementExists, String elementEdited, String elementDeleted) {
        this.elementNotFound = elementNotFound;
        this.elementAdded = elementAdded;
        this.elementIsNotActive = elementIsNotActive;
        this.elementExists = elementExists;
        this.elementEdited = elementEdited;
        this.elementDeleted = elementDeleted;
    }

    public String getElementDeleted() {
        return elementDeleted;
    }

    public String getElementNotFound() {
        return elementNotFound;
    }

    public String getElementAdded() {
        return elementAdded;
    }

    public String getElementIsNotActive() {
        return elementIsNotActive;
    }

    public String getElementExists() {
        return elementExists;
    }

    public String getElementEdited() {
        return elementEdited;
    }
}
