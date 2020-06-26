package com.zoopzam.partner.constants

enum class RxEventType {
    RETAILER_DETAIL_UPDATED,
    CART_ITEM_ADD_REMOVE,
    DEFAULT_ADDRRESS_UPDATE;

    companion object {

        fun get(name: String): RxEventType? {
            for (le in values()) {
                if (le.name.equals(name, ignoreCase = true)) {
                    return le
                }
            }
            return null
        }
    }

}