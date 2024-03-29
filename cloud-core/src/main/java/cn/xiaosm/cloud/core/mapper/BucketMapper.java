package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.Bucket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */
@Repository
public interface BucketMapper extends BaseMapper<Bucket> {

    @Select("select * from `bucket` WHERE `name` = #{name} AND `user_id` = #{userId}")
    Bucket selectByNameAndUserId(String name, Long userId);

}