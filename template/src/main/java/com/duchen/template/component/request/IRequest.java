package com.duchen.template.component.request;

public interface IRequest {

    //每一个请求对应一个mSequence，具体参考request中的mSequence，用于限制fifo
    int getSequence();

    void setSequence(int squence);

    String getUrl();

    void setUrl(String url);
}
