package ma.enset.services;


import io.grpc.stub.StreamObserver;
import ma.enset.entities.Client;
import ma.enset.interceptors.ClientMetaDatainterceptor;
import ma.enset.servers.ServerChatGrpc;
import ma.enset.subs.Chat;
import ma.enset.subs.chatServiceGrpc;

public class ChatServicesGrpc extends chatServiceGrpc.chatServiceImplBase{
    ClientMetaDatainterceptor clientMetaDatainterceptor;
    ServerChatGrpc serverChatGrpc;

    public ChatServicesGrpc(ServerChatGrpc serverChatGrpc) {
        this.serverChatGrpc = serverChatGrpc;
    }

    public ServerChatGrpc getServerChatGrpc() {
        return serverChatGrpc;
    }

    public void setServerChatGrpc(ServerChatGrpc serverChatGrpc) {
        this.serverChatGrpc = serverChatGrpc;
    }

    public ClientMetaDatainterceptor getClientMetaDatainterceptor() {
        return clientMetaDatainterceptor;
    }

    public void setClientMetaDatainterceptor(ClientMetaDatainterceptor clientMetaDatainterceptor) {
        this.clientMetaDatainterceptor = clientMetaDatainterceptor;
    }

    public ChatServicesGrpc(ClientMetaDatainterceptor clientMetaDatainterceptor) {
        this.clientMetaDatainterceptor = clientMetaDatainterceptor;
    }

    public ChatServicesGrpc() {
    }

    @Override
    public StreamObserver<Chat.ChatRequest> chat(StreamObserver<Chat.ChatResponse> responseObserver) {
        System.out.println("Here");
        return new StreamObserver<Chat.ChatRequest>() {
            @Override
            public void onNext(Chat.ChatRequest chatRequest) {
//                if(serverChatGrpc.getClientList().size()!=0){
//
//                }
                for(Client client :serverChatGrpc.getClientList()){
                    if(client.getResponseStreamObserver()==null){
                        serverChatGrpc.getClientList().get(serverChatGrpc.getClientList().size()-1).setResponseStreamObserver(responseObserver);
                        break;
                    }
                    if(responseObserver==client.getResponseStreamObserver()){
                        break;
                        // on va pas  set setResponseStreamObserver
                    }
                }
//                serverChatGrpc.getClientList().get(serverChatGrpc.getClientList().size()-1).setResponseStreamObserver(responseObserver);
                serverChatGrpc.brodcastingMessage(chatRequest,responseObserver);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("....End");
            }
        };
    }
}
