package com.rjxy.Repository;

import com.rjxy.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findAllByToIdAndToType(Integer toId, Boolean toType);

    List<Notice> findAllByToIdAndToTypeOrderByIdDesc(Integer toId, Boolean toType);

    List<Notice> findUnreadByToIdAndToTypeAndIsDealt(Integer toId, Boolean toType, Boolean isDealt);

}
