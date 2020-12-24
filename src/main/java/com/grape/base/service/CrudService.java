package com.grape.base.service;

import java.io.Serializable;

/**
 * @author duwenlei
 **/
public interface CrudService<T, ID extends Serializable> extends
        InsertService<T, ID>,
        SelectService<T, ID>,
        UpdateService<T, ID>,
        DeleteService<T, ID> {
}
