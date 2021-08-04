package com.rjxy.Service;

import com.rjxy.Entity.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getUserNoticeListByToId (Integer toId,Boolean toType);
}
