package com.example.demo.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.example.demo.model.entity.BaseEntity;

public interface BaseRepository<T extends BaseEntity, ID> extends ListCrudRepository<T, ID> {

}
