package com.zoopzam.photofinder.constants


object Constants {

    const val GALLERY: Int = 1000

    const val AWS_BUCKET_NAME = "zoopzam"
    const val S3_BASE_URL = "https://zoopzam.s3.ap-south-1.amazonaws.com"

    const val DATABASE = "dd.db"
    const val PRODUCT: String = "product"
    const val SCREEN: String = "screen"
    const val PRODUCT_ID: String = "product_id"
    const val RETAILER_ID: String = "retailer_id"
    const val GENDER_ID: String = "gender_id"
    const val PRODUCT_TYPE_ID: String = "product_type_id"
    const val PRODUCT_PAGINATE: String = "product_paginate"
    const val PRODUCT_SCROLL: String = "product_scroll"
    const val PRODUCT_HAS_MORE: String = "product_has_more"

    const val ADD_CART_ITEM: String = "add_cart_item"
    const val REMOVE_CART_ITEM: String = "remove_cart_item"
    const val UPDATE_CART_ITEM: String = "update_cart_item"

    const val CART_ITEM_UPDATE_ADD: String = "cart_item_update_add"
    const val CART_ITEM_UPDATE_REMOVE: String = "cart_item_update_remove"

    const val HOME_PAGINATE: String = "home_paginate"
    const val RETAILER_PRODUCTS_PAGINATE: String = "retailer_products_paginate"
    const val HOME_SCROLL: String = "home_scroll"
    const val RETAILER_PRODUCTS_SCROLL: String = "retailer_products_scroll"
    const val ORDER_ACCEPT: String = "accepted"
    const val ORDER_REJECT: String = "declined"
    const val TASK: String = "task"

    const val OPEN = "open"
    const val CLOSED = "closed"
    const val ADDRESS: String = "address"
    const val ADDRESS_PAGINATE: String = "address_paginate"
    const val ADDRESS_SCROLL: String = "address_scroll"
    const val ADD_ADDRESS: String = "add_address"
    const val EDIT_ADDRESS: String = "edit_address"
    const val REMOVE_ADDRESS: String = "remove_address"
    const val SET_AS_DEFAULT_ADDRESS: String = "set_as_default_address"


    interface HOME_ITEM_TYPE {
        companion object {
            const val IMAGE = "image"
            const val FOLDER = "folder"

        }
    }

    interface ORDER_STATUS {
        companion object {
            const val ON_WAY = "on-way"
            const val ARRIVED_AT_SHOP = "arrived-at-shop"
            const val ORDER_PICKED = "order-picked"
            const val ARRIVED_AT_CONSUMER = "arrived-at-consumer"
            const val DELIVERED = "delivered"
            const val NOT_DELIVERED = "not-delivered"
        }
    }
}