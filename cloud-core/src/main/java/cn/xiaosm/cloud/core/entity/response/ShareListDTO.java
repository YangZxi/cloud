package cn.xiaosm.cloud.core.entity.response;

import cn.xiaosm.cloud.core.entity.Share;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShareListDTO extends Share {

    private String shareLabel;

    public ShareListDTO(Share share) {
        super(share);
    }

}
