/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorMapper
 * Author:   Young
 * Date:     2020/6/16 10:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.mapper;

import cn.xiaosm.cloud.core.admin.entity.Prop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Repository
public interface PropMapper extends BaseMapper<Prop> {

    @Select("select * from `properties` where prop_key = #{propKey}")
    Prop selectByPropKey(String propKey);

    @Select("select * from `properties` where prop_key like concat(#{prefix}, '%')")
    List<Prop> listAllByPrefix(String prefix);

}