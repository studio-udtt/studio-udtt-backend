package com.udtt.backend.stat.repository;

import com.udtt.backend.stat.entity.SiteStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteStatRepository extends JpaRepository<SiteStat, Long> {

    List<SiteStat> findAllByOrderByIdAsc();
}