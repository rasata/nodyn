package org.projectodd.nodyn.net;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.projectodd.nodyn.CallbackResult;
import org.projectodd.nodyn.EventSource;

import java.net.SocketAddress;


/**
 * @author Bob McWhirter
 */
public class NetServerHandler extends ChannelDuplexHandler {

    protected final NetServerWrap server;

    public NetServerHandler(NetServerWrap server) {
        this.server = server;
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise future) throws Exception {
        future.addListener( new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                NetServerHandler.this.server.emit( "listening", CallbackResult.EMPTY_SUCCESS );
            }
        });
        super.bind( ctx, localAddress, future );
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
        future.addListener( new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                NetServerHandler.this.server.checkClose();
            }
        });
        super.close(ctx, future);
    }

}
