/**
 * Copyright: 2019-2020
 * FileName: PropEnum
 * Author:   Young
 * Date:     2020/6/28 12:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.entity.enums;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/28
 * @since 1.0.0
 */
public enum PropType implements BaseEnum<String> {

    EMAIL
    , FILE_TYPE
    , APP_CONFIG
    ;

    @Override
    public String getValue() {
        return this.name();
    }
}