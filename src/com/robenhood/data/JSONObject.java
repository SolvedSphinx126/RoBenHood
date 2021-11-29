package com.robenhood.data;

/**
 * The interface that allows JSON to convert Objects stored within JSON into
 *
 * @author Ben Morrison
 */
public interface JSONObject {

    /**
     * The abstract method that is implemented by classes that implement JSONObject.
     * @return JSON containing all the save data of the object.
     */
    public JSON toJSON();
}
