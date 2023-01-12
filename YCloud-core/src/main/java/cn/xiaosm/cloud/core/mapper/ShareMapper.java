package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.Share;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
@Repository
public interface ShareMapper extends BaseMapper<Share> {

    @Select("SELECT * FROM `share` WHERE `id` = #{uuid}")
    Share selectById(String id);

}