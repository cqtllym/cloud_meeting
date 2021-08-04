package com.rjxy.Service.Impl;

import com.rjxy.Entity.Notice;
import com.rjxy.Repository.NoticeRepository;
import com.rjxy.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public List<Notice> getUserNoticeListByToId (Integer toId, Boolean toType) {
        return noticeRepository.findUnreadByToIdAndToTypeAndIsDealt(toId, toType, false);
    }

}
