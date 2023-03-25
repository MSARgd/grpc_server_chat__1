package ma.enset.servers;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.entities.Client;
import ma.enset.interceptors.ClientMetaDatainterceptor;
import ma.enset.services.ChatServicesGrpc;
import ma.enset.subs.Chat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ServerChatGrpc {
    List<Client> clientList = new ArrayList<>();

    public List<Client> getClientList() {
        return clientList;
    }

    public void addClient(Client client){
        clientList.add(client);
    }
    public void removeClient(Client client){
        clientList.remove(client);
    }
    public void startServer(ChatServicesGrpc chatServicesGrpc) throws IOException, InterruptedException {
        ClientMetaDatainterceptor clientMetaDatainterceptor = new ClientMetaDatainterceptor();
        clientMetaDatainterceptor.setServerChatGrpc(this);
        chatServicesGrpc.setClientMetaDatainterceptor(clientMetaDatainterceptor);
        Server server = ServerBuilder.forPort(2023)
                .addService(chatServicesGrpc)
                .intercept(clientMetaDatainterceptor)
                .build();
        server.start();
        server.awaitTermination();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatServicesGrpc chatServicesGrpc = new ChatServicesGrpc();
        ServerChatGrpc serverChatGrpc = new ServerChatGrpc();
        chatServicesGrpc.setServerChatGrpc(serverChatGrpc);
        serverChatGrpc.startServer(chatServicesGrpc);
    }
    public void brodcastingMessage(Chat.ChatRequest chatRequest,StreamObserver<Chat.ChatResponse> chatResponseStreamObserver){
        System.out.println("Hello Every One ....!");
        Chat.ChatResponse chatResponse = Chat.ChatResponse.newBuilder()
                .setChatMessage(chatRequest.getChatMessage())
                .setChatId(chatRequest.getChatId())
                .setUserId(chatRequest.getUserId())
                .build();
        for (Client client: clientList) {
            if(client.getResponseStreamObserver()!=chatResponseStreamObserver){
                client.getResponseStreamObserver().onNext(chatResponse);
                System.out.println(".........!");
            }
        }
    }

}