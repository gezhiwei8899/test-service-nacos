package com.duoying.test.mapper.youxian_qa;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityHelperQaMapper {
    List<Integer> listByRemark(@Param("remark") String remark);
}
