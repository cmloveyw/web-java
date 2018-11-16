/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package common;

/**
 * ControllerResult : 返回内容
 *
 * @author chenming
 * @version 1.00
 * @since 2017-10-23 15:40
 */
public class ControllerResult {
    /**
     * 内容
     */
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }
}
