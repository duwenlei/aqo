package com.grape.base.service;

import com.grape.base.query.Page;
import com.grape.base.query.SearchFilter;
import com.grape.base.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author duwenlei
 **/
public abstract class SuperService<T, ID extends Serializable, R extends BaseRepository<T, ID>> implements CrudService<T, ID> {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private R repository;

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Iterable<ID> ids) {
        Iterator<ID> iterator = ids.iterator();
        while (iterator.hasNext()) {
            delete(iterator.next());
        }
    }

    @Override
    public void clear() {
        repository.deleteAllInBatch();
    }

    @Override
    public T insert(T t) {
        return repository.save(t);
    }

    @Override
    public T get(ID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<T> query(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<T> queryAll() {
        return repository.findAll();
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public Page<T> queryPage(Page<T> page) {
        Pageable pageable = null;
        if (page.isOpenSort()) {
            pageable = PageRequest.of(page.getCurrentNum() - 1, page.getPageSize(), page.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, page.getOrderByField());
        } else {
            pageable = PageRequest.of(page.getCurrentNum() - 1, page.getPageSize());
        }
        // TOTO 以后处理
        SearchFilter searchFilter = page.getFilters();
        Specification specification = searchFilter.getSpecification();
        org.springframework.data.domain.Page pageResult = repository.findAll(specification, pageable);
        page.setTotal(Integer.parseInt(pageResult.getTotalElements() + ""));
        page.setRecords(pageResult.getContent());
        return page;
    }

    @Override
    public List<T> queryAll(SearchFilter filter) {
        return queryAll(filter, null);
    }

    @Override
    public List<T> queryAll(SearchFilter filter, Sort sort) {
        Specification specification = filter.getSpecification();
        if (Objects.nonNull(sort)) {
            repository.findAll(specification, sort);
        }
        return repository.findAll(specification);
    }
}
