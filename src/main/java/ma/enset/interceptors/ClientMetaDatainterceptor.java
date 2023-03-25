package ma.enset.interceptors;

import io.grpc.*;
import ma.enset.entities.Client;
import ma.enset.servers.ServerChatGrpc;

public class ClientMetaDatainterceptor implements ServerInterceptor {
    ServerChatGrpc serverChatGrpc;

    public ServerChatGrpc getServerChatGrpc() {
        return serverChatGrpc;
    }

    public void setServerChatGrpc(ServerChatGrpc serverChatGrpc) {
        this.serverChatGrpc = serverChatGrpc;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        ServerCall.Listener<ReqT> reqTListener = serverCallHandler.startCall(serverCall,metadata);
        String metadataValue = metadata.get(Metadata.Key.of("metadata-key", Metadata.ASCII_STRING_MARSHALLER));
        String clientIp = serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
        serverChatGrpc.addClient(new Client(clientIp));
        return  new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(reqTListener) {
            @Override
            protected ServerCall.Listener<ReqT> delegate() {
                return super.delegate();
            }

            @Override
            public void onMessage(ReqT message) {
                super.onMessage(message);
            }

            @Override
            public void onHalfClose() {
                super.onHalfClose();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onReady() {
                super.onReady();
            }

            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
}
