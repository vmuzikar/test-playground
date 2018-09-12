package org.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class EmbeddedProxy {
    public static void main(String[] args) {
        HttpProxyServer proxy = DefaultHttpProxyServer.bootstrap()
                .withTransparent(true)
                .withFiltersSource(new HttpFiltersSourceAdapter() {
                    @Override
                    public HttpFilters filterRequest(HttpRequest originalRequest) {
                        return new HttpFiltersAdapter(originalRequest) {
                            @Override
                            public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                if (!originalRequest.getUri().equals("http://non-existing-domain/")) {
                                    return null;
                                }

                                final String answer = "Hello World!";
                                ByteBuf buffer = Unpooled.wrappedBuffer(answer.getBytes());
                                HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
                                HttpHeaders.setContentLength(response, buffer.readableBytes());
                                HttpHeaders.setHeader(response, HttpHeaders.Names.CONTENT_TYPE, "plain/text");
                                return response;
                            }
                        };
                    }
                })
                .start();
    }
}
