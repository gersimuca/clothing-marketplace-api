package com.gersimuca.cm.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable>
        extends JpaRepository<T, I>, PagingAndSortingRepository<T, I>, JpaSpecificationExecutor<T> {
}